package com.apache.fastandroid.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.NewsSummary;
import com.apache.fastandroid.support.sdk.TestSdk;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/11/27.
 */

public class TestWorkTaskFragment extends ABaseFragment {

    public static TestWorkTaskFragment newFragment() {
         Bundle args = new Bundle();
         TestWorkTaskFragment fragment = new TestWorkTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestWorkTaskFragment.class,args);
    }

    @ViewInject(idStr = "tv_result")
    private TextView tv_result;

    @Override
    public int inflateContentView() {
        return R.layout.test_fragment_work_task;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        findViewById(R.id.btn_test_worktask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDataWorkTask().execute();
            }
        });
        findViewById(R.id.btn_test_asynctask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDataAsyncTask().execute();
            }
        });
    }

    private  class LoadDataWorkTask extends WorkTask<Void,Void,NewsSummary>{

        @Override
        protected void onPrepare() {
            super.onPrepare();
            ViewUtils.createProgressDialog(getActivity(), "加载数据中",0).show();
        }

        @Override
        public NewsSummary workInBackground(Void... params) throws TaskException {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return TestSdk.newInstance().testWorkTask();
        }

        @Override
        protected void onSuccess(NewsSummary newsSummary) {
            super.onSuccess(newsSummary);
            _success(newsSummary);
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            _failed(exception);
        }

        @Override
        protected void onFinished() {
            super.onFinished();
            ViewUtils.dismissProgressDialog();
        }
    }


    private  class LoadDataAsyncTask extends AsyncTask<Void,Void,NewsSummary> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewUtils.createProgressDialog(getActivity(), "加载数据中",0).show();

        }

        @Override
        protected NewsSummary doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return TestSdk.newInstance().testAsyncTask();
        }

        @Override
        protected void onPostExecute(NewsSummary newsSummary) {
            super.onPostExecute(newsSummary);
            if (newsSummary != null){
                _success(newsSummary);

            }else {

            }
            ViewUtils.dismissProgressDialog();

        }
    }


    private  void _success(NewsSummary newsSummary){
        tv_result.setText(newsSummary.postid);
    }
    private  void _failed(TaskException exception){
        tv_result.setText(exception.getCode());
    }
}
