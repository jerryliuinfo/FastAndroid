package com.apache.fastandroid.demo.blacktech.viewpump;

import android.content.res.TypedArray;

import com.apache.fastandroid.R;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.Toolbar;
import dev.b3nedikt.viewpump.InflateResult;
import dev.b3nedikt.viewpump.Interceptor;

/**
 * This is an example of a post-inflation interceptor that modifies the properties of a view
 * after it has been created. Here we prefix the text for any view that has been replaced with
 * a custom version by the {@link CustomTextViewInterceptor}.
 */
public class TextUpdatingInterceptor implements Interceptor {

    @NotNull
    @Override
    public InflateResult intercept(Chain chain) {
        InflateResult result = chain.proceed(chain.request());
        if (result.getView() instanceof CustomTextView) {
            CustomTextView textView = (CustomTextView) result.getView();

            TypedArray a = result.getContext().obtainStyledAttributes(
                    result.getAttrs(), new int[]{android.R.attr.text}
            );

            try {
                CharSequence text = a.getText(0);
                if (text != null && text.length() > 0) {
                    if (text.toString().startsWith("\n")) {
                        text = text.toString().substring(1);
                    }
                    textView.setText(textView.getContext().getString(R.string.custom_textview_prefixed_text, text));
                }
//                int color = a.getColor(1, Color.parseColor("#00ff00"));
//                if (color != Color.parseColor("#00ff00")){
//                    textView.setTextColor(Color.parseColor("#ff0000"));
//                }
            } finally {
                a.recycle();
            }
        }
        if(result.getView() instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) result.getView();
            toolbar.setTitle("Updated Toolbar Title");
        }
        return result;
    }
}
