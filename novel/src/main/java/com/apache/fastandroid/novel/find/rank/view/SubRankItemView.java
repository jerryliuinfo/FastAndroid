package com.apache.fastandroid.novel.find.rank.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.BookBean;
import com.apache.fastandroid.novel.find.detail.BookDetailActivity;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/10/10.
 */

public class SubRankItemView extends ARecycleViewItemViewHolder<BookBean> {
    public static final int LAY_RES = R.layout.novel_item_sub_category_list;

    @ViewInject(idStr = "ivSubCateCover")
    ImageView ivSubCateCover;

    @ViewInject(idStr = "tvSubCateTitle")
    TextView tvSubCateTitle;

    @ViewInject(idStr = "tvSubCateAuthor")
    TextView tvSubCateAuthor;

    @ViewInject(idStr = "tvSubCateShort")
    TextView tvSubCateShort;

    @ViewInject(idStr = "tvSubCateMsg")
    TextView tvSubCateMsg;

    public SubRankItemView(Activity context, View itemView) {
        super(context, itemView);
    }
    @Override
    public void onBindData(View convertView, final BookBean item, int position) {
        ImageLoaderManager.getInstance().showImage(ivSubCateCover, NovelConstans.IMG_BASE_URL + item.cover,getContext());
        tvSubCateTitle.setText(item.title);
        String subCateAuthor = (item.author == null ? "未知" : item.author) + " | " + (item.majorCate == null ? "未知" : item.majorCate);
        tvSubCateAuthor.setText(subCateAuthor);
        tvSubCateShort.setText(item.shortIntro);

        String subCateMsg = String.format(ResUtil.getString(R.string.category_book_msg),item.latelyFollower, TextUtils.isEmpty(item.retentionRatio)? "0":item.retentionRatio);
        tvSubCateMsg.setText(subCateMsg);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.launch(getContext(),item._id);
            }
        });

    }
}
