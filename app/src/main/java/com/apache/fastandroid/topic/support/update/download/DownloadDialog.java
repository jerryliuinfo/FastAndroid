package com.apache.fastandroid.topic.support.update.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.support.sdk.bean.UpdateBean;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class DownloadDialog extends BaseActivity {


    private static final String EXTRA_UPDATE_BEAN = "EXTRA_UPDATE_BEAN";

    DownloadTask sDownloadTask;

    private ImageView close;
    public ProgressBar progressBar;
    public TextView textView;

    private UpdateBean mUpdateBean;

    public static void launch(Activity from, UpdateBean bean) {
        Intent intent = new Intent(from, DownloadDialog.class);
        intent.putExtra(EXTRA_UPDATE_BEAN,bean);
        from.startActivity(intent);
    }


    public void showDownloadProgress(int progress){
        progressBar.setProgress(progress);
        textView.setText(progress + "%");
    }
    public void downloadFinished(){
        finish();
    }

    public void downloadFailed(){
        Toast.makeText(this, "下载失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public int inflateContentView() {
        return R.layout.download_dialog;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        close = (ImageView) findViewById(R.id.downloaddialog_close);
        progressBar = (ProgressBar) findViewById(R.id.downloaddialog_progress);
        textView = (TextView) findViewById(R.id.downloaddialog_count);

        if (savedInstanceState == null){
            mUpdateBean = (UpdateBean) getIntent().getSerializableExtra(EXTRA_UPDATE_BEAN);
        }else {
            mUpdateBean = (UpdateBean) savedInstanceState.getSerializable(EXTRA_UPDATE_BEAN);
        }

        beginDownload();
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cancelTask();
                finish();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_UPDATE_BEAN,mUpdateBean);
    }


    private void beginDownload(){
        cancelTask();
        sDownloadTask = new DownloadTask(this,mUpdateBean);
        sDownloadTask.execute();
    }

    private  void cancelTask(){
        if (sDownloadTask != null){
            sDownloadTask.cancel(true);
            sDownloadTask = null;
        }
    }
}
