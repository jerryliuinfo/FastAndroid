package com.apache.fastandroid.widget;

import android.content.Context;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.apache.fastandroid.R;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by jerryliu on 2017/6/29.
 */

public class SplashCountDownView extends androidx.appcompat.widget.AppCompatTextView {
    public static final String TAG = SplashCountDownView.class.getSimpleName();

    private MyCountDownTimer countDownTimer;
    private long millisInFuture = 5000;
    private long countDownInterval = 1000;
    private long mDealyMilles = 500; //显示正在跳转后 延迟多久后真正开始跳转

    private ICountDownCallback callback;

    private String mCountDowningText; //倒计时显示的文字
    private String mCountDownEndText; //倒计时完成显示的文字

    public SplashCountDownView(Context context) {
        super(context);
        init();
    }

    public SplashCountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SplashCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundDrawable(ResUtil.getDrawable(R.drawable.splash_countdown_bg));

        mCountDownEndText = ResUtil.getString(R.string.splash_redirecting);
        mCountDowningText = ResUtil.getString(R.string.splash_countdown_count);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    cancel();
                    callback.onClicked();
                }
            }
        });
    }

    class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            setText(String.format(mCountDowningText,millisUntilFinished / 1000));
            if (callback != null){
                callback.onTick(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            NLog.d(TAG, "onFinish");
            setText(mCountDownEndText);
            if (callback != null){
                callback.onFinish(mDealyMilles);
            }
        }
    }


    public void start(){
        countDownTimer = new MyCountDownTimer(millisInFuture,countDownInterval);
        countDownTimer.start();
        if (callback != null){
            callback.onStart();
        }
    }

    public void cancel(){
        if (countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    public SplashCountDownView setDuraionn(long mDuraionn) {
        this.millisInFuture = mDuraionn;

        return this;
    }

    public SplashCountDownView setCountDowningText(String countDowningText) {
        this.mCountDowningText = countDowningText;
        return this;
    }

    public SplashCountDownView setCountEndText(String mEndText) {
        this.mCountDownEndText = mEndText;
        return this;
    }

    public SplashCountDownView setCallback(ICountDownCallback callback) {
        this.callback = callback;
        return this;
    }


    public static class CountDownCallbackImpl implements ICountDownCallback{

        @Override
        public void onStart() {

        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish(long delay) {

        }

        @Override
        public void onClicked() {

        }
    }





    public interface ICountDownCallback{
        void onStart();
        void onTick(long millisUntilFinished);
        void onFinish(long delay);
        //点击的时候调用
        void onClicked();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
