package com.apache.fastandroid.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.sdk.MarathonSdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/12/14.
 */

public class MarathonFragment  extends ABaseFragment {
    @ViewInject(idStr = "tv_result")
    TextView tv_result;
    @Override
    public int inflateContentView() {
        return R.layout.activity_marathon;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        findViewById(R.id.btn_laod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDataTask().execute();
            }
        });
        findViewById(R.id.btn_laod_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDataTaskByPost().execute();
            }
        });



    }

    class LoadDataTask extends WorkTask<Void,Void,String>{

        @Override
        protected void onPrepare() {
            super.onPrepare();
           showLoadingDialog();
        }

        @Override
        public String workInBackground(Void... params) throws TaskException {
            //return MarathonSdk.newInstance().getCategory();
            return MarathonSdk.newInstance().getCategory2();
        }

        @Override
        protected void onSuccess(String s) {
            super.onSuccess(s);
            tv_result.setText(s);
        }

        @Override
        protected void onFinished() {
            super.onFinished();
            hideLoadingDialog();
        }
    }
    class LoadDataTaskByPost extends WorkTask<Void,Void,String>{

        @Override
        protected void onPrepare() {
            super.onPrepare();
            showLoadingDialog();
        }

        @Override
        public String workInBackground(Void... params) throws TaskException {
            return MarathonSdk.newInstance().getCategoryByPost();
        }

        @Override
        protected void onSuccess(String s) {
            super.onSuccess(s);
            tv_result.setText(s);
        }

        @Override
        protected void onFinished() {
            super.onFinished();
            hideLoadingDialog();
        }
    }
}
