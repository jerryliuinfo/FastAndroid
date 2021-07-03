package com.apache.fastandroid.demo.temp;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.fragment.BaseFragment;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2021/1/20.
 */
public class SpanableStringFragment extends BaseFragment {
    private TextView mChildrenPrivacyText;
    @Override
    public int inflateContentView() {
        return R.layout.fragment_spannable_string;
    }

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        mChildrenPrivacyText = findViewById(R.id.tv_content);


        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        //《儿童隐私政策》
        SpannableString cpp = new SpannableString(getContext().getResources().getString(R.string.experience_info3));
        //若您是16周岁以下的未成年人，请确保您的父母或监护人已经仔细阅读%1$s，并进行确认。
        String[] formatStrings = setSpannableString(getContext().getResources().getString(R.string.experience_info2),
                cpp.toString());
        cpp.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                KidsUtils.onPrivacyPolicyClick(getContext());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.edu_color_blue));
//                ds.setUnderlineText(false);
            }

        }, 0, cpp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(formatStrings[0]).append(cpp).append(formatStrings[1]);
        mChildrenPrivacyText.setText(spannableStringBuilder);
        mChildrenPrivacyText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String[] setSpannableString(String text, String format) {
        text = String.format(text, format);
        return text.split(format, 2);
    }
}
