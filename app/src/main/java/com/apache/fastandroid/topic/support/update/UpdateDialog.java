package com.apache.fastandroid.topic.support.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.support.sdk.bean.UpdateBean;
import com.apache.fastandroid.topic.support.update.download.DownloadDialog;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class UpdateDialog extends BaseActivity {
    private static final String EXTRA_UPDATE_BEAN = "EXTRA_UPDATE_BEAN";

    public static void launchForResult(Activity from, int requestCode,UpdateBean updateBean) {
        Intent intent = new Intent(from, UpdateDialog.class);
        intent.putExtra(EXTRA_UPDATE_BEAN,updateBean);
        from.startActivityForResult(intent,requestCode);
    }

    private TextView yes, no;
    private TextView tv_version, tv_changelog;
    private UpdateBean mUpdateBean;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_UPDATE_BEAN, mUpdateBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            mUpdateBean = (UpdateBean) getIntent().getSerializableExtra(EXTRA_UPDATE_BEAN);
        }else {
            mUpdateBean = (UpdateBean) savedInstanceState.getSerializable(EXTRA_UPDATE_BEAN);
        }


        yes = (TextView) findViewById(R.id.updatedialog_yes);
        no = (TextView) findViewById(R.id.updatedialog_no);
        tv_version = (TextView) findViewById(R.id.title);
        tv_changelog = (TextView) findViewById(R.id.updatedialog_text_changelog);

        tv_version.setText("发现新版本: V" + mUpdateBean.version);
        tv_changelog.setText("更新日志：\n" + mUpdateBean.description);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DownloadDialog.launch(UpdateDialog.this,mUpdateBean);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });
    }


}
