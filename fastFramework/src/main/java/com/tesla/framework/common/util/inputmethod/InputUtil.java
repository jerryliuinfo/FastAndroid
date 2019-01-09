package com.tesla.framework.common.util.inputmethod;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tesla.framework.common.util.handler.HandlerUtil;

/**
 * Created by 01370340 on 2018/4/28.
 */

public class InputUtil {

    /**
     * 输入框获取焦点
     *
     * @param mTextInput
     */
    public static void getFocusForView(View mTextInput) {
        mTextInput.setEnabled(true);
        mTextInput.setFocusable(true);
        mTextInput.requestFocus();
    }

    /**
     * 显示键盘
     */
    public static void showInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     *
     * @param v
     */
    public static void hideInput(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    public static void showInput(Context context, InputMethodManager imm) {
        if (null == imm) {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     *
     * @param v
     */
    public static void hideInput(View v, Context context, InputMethodManager imm) {
        if (null == imm) {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }



    /**
     * 延迟一段时间弹出输入法(某些情况下,EditText还没获取焦点的时候调用 showInput无效)
     */
    public static void showInputMethod(final Context context, final InputMethodManager imm, long delay){
        HandlerUtil.runUITask(new Runnable() {
            @Override
            public void run() {
                showInput(context, imm);
            }
        },delay);
    }
}
