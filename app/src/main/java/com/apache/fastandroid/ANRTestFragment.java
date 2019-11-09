package com.apache.fastandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.apache.fastandroid.artemis.base.BaseFragment;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.component.handler.FHandler;
import com.tesla.framework.component.handler.FLooper;
import com.tesla.framework.component.handler.FMessage;
import java.util.UUID;

/**
 * author: 01370340
 * data: 2019-10-14
 * description:
 */
public class ANRTestFragment extends BaseFragment {
    @Override
    public int inflateContentView() {
        return R.layout.fragment_anr;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastBus.getInstance().post(new UserEvent("zhangsan",30));
            }
        });
        findViewById(R.id.btn_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               testFastHandler();
            }
        });
    }

    private void testFastHandler(){
        FLooper.prepare();
        final FHandler handler = new FHandler(){
            @Override
            public void handleMessage(FMessage msg) {
                super.handleMessage(msg);
                FrameworkLogUtil.d("Thread : %s recevied %s",Thread.currentThread().getName(), msg.toString());
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    super.run();

                    while (true){
                        FMessage msg = new FMessage();
                        msg.what = finalI;
                        msg.obj = Thread.currentThread().getName() + " send "+ UUID.randomUUID().toString();
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        FLooper.loop();

    }
}
