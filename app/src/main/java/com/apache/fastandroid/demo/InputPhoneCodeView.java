package com.apache.fastandroid.demo;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.blankj.utilcode.util.ColorUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 验证码组件
 */
public class InputPhoneCodeView extends LinearLayout {

    private Context context;
    private ViewHolder holder;
    private OnClickListener onClickListener;

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }

    public void setOnSendListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    private boolean isfinish  = true;
    private CountDownTimer timer=new CountDownTimer(60*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            isfinish = false;
            //倒计时过程中不允许点击
            holder.btnGetCode.setEnabled(false);
            holder.get_code_divider.setEnabled(false);
            holder.btnGetCode.setTextColor(ContextCompat.getColor(context, R.color.login_send_verify_code_disable));
            holder.btnGetCode.setText(context.getString(R.string.login_register_timer_num,millisUntilFinished/1000));


        }

        @Override
        public void onFinish() {
            isfinish = true;
            if (mListener != null){
                mListener.onFinish();
            }else {
                updateBtnGetCodeColor(true);
            }
            holder.btnGetCode.setText(context.getString(R.string.login_register_get_phone_code));
        }
    };
    private OnTimeFinishListener mListener;

    public void setListener(OnTimeFinishListener mListener) {
        this.mListener = mListener;
    }

    public interface OnTimeFinishListener{
        void onFinish();
    }

    public void onFinish(){
        updateBtnGetCodeColor(true);
    }

    public void updateBtnGetCodeColor(boolean enable){
        holder.btnGetCode.setEnabled(enable);
        holder.btnGetCode.setTextColor(!enable? ColorUtils.getColor(R.color.login_send_verify_code_disable):
                ContextCompat.getColor(context,R.color.login_send_verify_code_enable));
    }

    public InputPhoneCodeView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public InputPhoneCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public InputPhoneCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 设备获取验证码按钮是否可以点击
     * @param enable
     */
    public void setGetCodeButtonEnable(boolean enable){
        if(isfinish){
            updateBtnGetCodeColor(enable);
        }
    }


    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.register_view_input_phone_code, this, true);
        holder = new ViewHolder(view);
    }

    public String getCodeStr(){
        return holder.edtCode.getText().toString().trim();
    }

    public void addTextChangedListener(TextWatcher textWatcher){
        holder.edtCode.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher){
        holder.edtCode.removeTextChangedListener(textWatcher);
    }

    public void setRootBackground(@DrawableRes int res){
        holder.rootView.setBackgroundResource(res);
    }

    class ViewHolder {
        TextView btnGetCode;
        EditText edtCode;
        View get_code_divider;
        View rootView;

        ViewHolder(View view) {
            btnGetCode = findViewById(R.id.btn_get_code);
            edtCode = findViewById(R.id.edt_code);
            get_code_divider = findViewById(R.id.get_code_divider);
            rootView  = findViewById(R.id.rootView);
            btnGetCode.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onClickListener) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }

    }


}
