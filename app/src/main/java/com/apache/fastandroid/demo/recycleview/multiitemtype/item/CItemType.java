package com.apache.fastandroid.demo.recycleview.multiitemtype.item;

import com.apache.fastandroid.databinding.ItemCBinding;
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.BeanC;
import com.tencent.lib.multi.core.SimpleItemType;

import androidx.annotation.NonNull;

/**
 * Author：岑胜德 on 2021/1/6 18:04
 * <p>
 * 说明：
 */
public class CItemType extends SimpleItemType<BeanC, ItemCBinding> {

    @Override
    public boolean isMatched(Object bean, int position) {
        return  bean instanceof BeanC;
    }

    @Override
    public void onBindView(@NonNull ItemCBinding binding,
                           @NonNull BeanC bean, int position) {
        binding.tvC.setText(bean.text);
    }
}
