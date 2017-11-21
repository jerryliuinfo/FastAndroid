package com.apache.fastandroid.novel.find.detail.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.HotReviewBean;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.common.util.format.FormatUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.component.imageloader.ImageLoaderOptions;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;


/**
 * Created by 01370340 on 2017/9/24.
 */

public class HotReviewItemView extends ARecycleViewItemViewHolder<HotReviewBean.Reviews> {

    public static final int LAY_RES = R.layout.novel_item_book_detai_hot_review_list;

    @ViewInject(idStr = "ivBookCover")
    ImageView ivBookCover;

    @ViewInject(idStr = "tvBookTitle")
    TextView tvBookTitle;

    @ViewInject(idStr = "tvBookType")
    TextView tvBookType;

    @ViewInject(idStr = "tvTitle")
    TextView tvTitle;

    @ViewInject(idStr = "tvContent")
    TextView tvContent;

    @ViewInject(idStr = "tvHelpfulYes")
    TextView tvHelpfulYes;


    public HotReviewItemView(Activity context, View itemView) {
        super(context, itemView);
    }


    @Override
    public void onBindData(View convertView, HotReviewBean.Reviews item, int position) {
        if (!TextUtils.isEmpty(item.author.avatar)){
            ImageLoaderManager.getInstance().showImage(new ImageLoaderOptions.Builder(ivBookCover, NovelConstans.IMG_BASE_URL + item.author.avatar,getContext()).placeholder(R.drawable.cover_default).build());
        }
        tvBookTitle.setText(item.author.nickname);
        tvBookType.setText(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_user_lv),item.author.lv));
        tvTitle.setText(item.title);
        tvContent.setText(String.valueOf(item.content));
        tvHelpfulYes.setText(String.valueOf(item.helpful.yes));
    }

}
