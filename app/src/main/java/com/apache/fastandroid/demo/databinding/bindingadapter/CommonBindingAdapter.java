/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.demo.databinding.bindingadapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ClickUtils;
import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 * Create by KunMinX at 19/9/18
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void imageUrl(ImageView view, String url, Drawable placeHolder) {
        Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
    }

    @BindingAdapter(value = {"visible"}, requireAll = false)
    public static void visible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"textColor"}, requireAll = false)
    public static void setTextColor(TextView textView, int textColorRes) {
        textView.setTextColor(textView.getContext().getColor(textColorRes));
    }

    @BindingAdapter(value = {"selected"}, requireAll = false)
    public static void selected(View view, boolean select) {
        view.setSelected(select);
    }


    @BindingAdapter(value = {"onClickWithDebouncing"}, requireAll = false)
    public static void onClickWithDebouncing(View view, View.OnClickListener clickListener) {
        ClickUtils.applySingleDebouncing(view, clickListener);
    }
}
