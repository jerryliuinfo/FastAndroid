package com.tesla.framework.component.handler;

import com.tesla.framework.common.util.FrameworkLogUtil;
import java.util.UUID;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class HandlerTest {
    public static void main(String[] args) {

        FLooper.prepare();
        final FHandler handler = new FHandler(){
            @Override
            public void handleMessage(FMessage msg) {
                super.handleMessage(msg);
                FrameworkLogUtil.d("Thread : %s recevied %s",Thread.currentThread().getName(), msg.toString());
                //try {
                //    Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
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
                        FrameworkLogUtil.d("send msg :"+msg);
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        }
        FLooper.loop();

    }
}
