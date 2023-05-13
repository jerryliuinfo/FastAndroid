package com.apache.fastandroid.demo.recycleview.multiitemtype.item;

import android.view.View;
import android.widget.Toast;

import com.apache.fastandroid.databinding.ItemABinding;
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.BeanA;
import com.tencent.lib.multi.core.SimpleItemType;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author：岑胜德 on 2021/1/6 18:04
 * <p>
 * 说明：
 */
public class AItemType extends SimpleItemType<BeanA, ItemABinding> {

    public AItemType() {
        bind(this);
    }

    @Override
    public boolean isMatched(@Nullable Object bean, int position) {
        return bean instanceof BeanA;
    }


    @Override
    public void onBindView(@NonNull ItemABinding binding,
                           @NotNull BeanA itemBean,
                           int position) {
        binding.tvA.setText(itemBean.text);
    }

    /**
     * item点击事件
     * 注意 bean 类型，一定要与当前 ItemType 的 bean 类型对应。
     */
    @Keep
    private void onClickItem(View view, BeanA bean, int position) {
        Toast.makeText(view.getContext(), "点击事件：" + bean.text, Toast.LENGTH_SHORT).show();
    }
}
