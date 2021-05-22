package com.apache.fastandroid.demo.widget.qmui;

import android.content.Context;
import android.util.AttributeSet;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/5/4.
 */
public class KKRoundButton extends QMUIRoundButton {
    public static final String TAG = KKRoundButton.class.getSimpleName();
    public KKRoundButton(Context context) {
        super(context);
    }

    public KKRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KKRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

    }
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        NLog.d(TAG, "setPressed pressed: %s",pressed);
    }
}
