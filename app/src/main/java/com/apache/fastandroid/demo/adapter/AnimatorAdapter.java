package com.apache.fastandroid.demo.adapter;

import com.apache.fastandroid.R;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/4.
 */
public class AnimatorAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public AnimatorAdapter(@Nullable List<Status> data) {
        super(R.layout.layout_animation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        switch (helper.getLayoutPosition() % 3) {
            case 0:
                helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.mipmap.animation_img3);
                break;
            default:
                break;
        }
    }
}
