package com.apache.fastandroid.demo.blacktech.viewpump;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import dev.b3nedikt.viewpump.InflateRequest;
import dev.b3nedikt.viewpump.InflateResult;
import dev.b3nedikt.viewpump.Interceptor;

/**
 * This is an example of a pre-inflation interceptor that returns programmatically instantiated
 * CustomTextViews instead of inflating TextViews.
 */
public class CustomTextViewInterceptor implements Interceptor {

    @NotNull
    @Override
    public InflateResult intercept(Chain chain) {
        InflateRequest request = chain.request();
        View view = inflateView(request.getName(), request.getContext(), request.getAttrs());

        if (view != null) {
            return new InflateResult(view, request.getName(), request.getContext(), request.getAttrs());
        } else {
            return chain.proceed(request);
        }
    }


    @Nullable
    private View inflateView(String name, Context context, AttributeSet attrs) {
        if ("TextView".equals(name)) {
            return new CustomTextView(context, attrs);
        }
        return null;
    }
}
