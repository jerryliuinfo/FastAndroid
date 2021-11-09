package com.apache.fastandroid.demo.temp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.demo.temp.concurry.PlayerNew;
import com.tesla.framework.ui.fragment.BaseFragment;

/**
 * Created by Jerry on 2021/11/3.
 * 模拟死锁发生
 */
public class ConcurrencyDemoFragment extends BaseFragment {
    @Override
    public int inflateContentView() {
        return R.layout.fragment_concurry_demo;
    }



    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);



        findViewById(R.id.btn_sisuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerNew zhangsan = new PlayerNew("zhangsan");

                PlayerNew lisi = new PlayerNew("lisi");
                new Thread(zhangsan, "线程1").start();
                new Thread(lisi,"线程2").start();

                //只会打印出zhangsan pay 和 lisi pay
            }
        });

    }


}
