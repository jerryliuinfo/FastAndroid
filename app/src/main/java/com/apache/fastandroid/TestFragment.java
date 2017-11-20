package com.apache.fastandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.widget.bottomdialog.BottomDialog;

/**
 * Created by 01370340 on 2017/11/20.
 */

public class TestFragment extends ABaseFragment {

    public static TestFragment newFragment() {
         Bundle args = new Bundle();
         TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_test;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        Button btn_bottom_dialog = (Button) findViewById(R.id.btn_bottom_dialog);
        btn_bottom_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sample(v);
            }
        });
        Button btn_bottom_custom_dialog = (Button) findViewById(R.id.btn_bottom_custom_dialog);
        btn_bottom_custom_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleCustomView(v);
            }
        });
    }


    public void sample(View view) {
        new BottomDialog.Builder(getActivity())
                .setTitle("Awesome!")
                .setContent("Glad to see you like BottomDialogs! If you're up for it, we would really appreciate you reviewing us.")
                .setPositiveText("Google Play")
                .setNegativeText("Close")
                .show();
    }

    public void sampleCustomView(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.bottomdialog_layout, null);

        new BottomDialog.Builder(getActivity())
                .setTitle("Awesome!")
                .setContent("Glad to see you like BottomDialogs! If you're up for it, we would really appreciate you reviewing us.")
                .setCustomView(customView)
                .setPositiveText("Google Play")
                .setNegativeText("Close")
                .show();
    }
}
