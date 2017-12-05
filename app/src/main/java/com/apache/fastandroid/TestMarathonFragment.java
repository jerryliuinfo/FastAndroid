package com.apache.fastandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/12/5.
 */

public class TestMarathonFragment extends ABaseFragment {
    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestMarathonFragment.class,args);
    }
    @Override
    public int inflateContentView() {
        return R.layout.test_marathon;
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubmitDataTask().execute();
            }
        });
    }

    class SubmitDataTask extends WorkTask<Void,Void,Void> {


        @Override
        public Void workInBackground(Void... params) throws TaskException {

            return null;
        }
    }
}
