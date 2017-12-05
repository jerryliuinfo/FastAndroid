package com.apache.fastandroid.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/12/5.
 */

public class TestStartActivityResult extends ABaseFragment{
    @Override
    public int inflateContentView() {
        return R.layout.fragment_test_startactivity_result;
    }

    public static void launchForReulst(ABaseFragment fragment){
        FragmentContainerActivity.launchForResult(fragment,TestStartActivityResult.class,null,100);
    }
    /*public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestStartActivityResult.class,args);
    }*/

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        findViewById(R.id.btn_setResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("username", "zhangsan");
                getActivity().setResult(200,data);
                getActivity().finish();
            }
        });
    }
}
