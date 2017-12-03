package com.apache.fastandroid.novel.bookshelf.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.bookshelf.ReadActivity;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.util.NovelUtils;
import com.tesla.framework.common.util.format.TimeFormatUtil;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class BookSelfItemView extends ARecycleViewItemViewHolder<RecommendBook> {

    public BookSelfItemView(Activity context, View itemView) {
        super(context, itemView);
    }


    @ViewInject(idStr = "tvRecommendTitle")
    TextView tvRecommendTitle;
    @ViewInject(idStr = "tvLatelyUpdate")
    TextView tvLatelyUpdate;
    @ViewInject(idStr = "tvRecommendShort")
    TextView tvRecommendShort;
    @ViewInject(idStr = "ivTopLabel")
    ImageView ivTopLabel;
    @ViewInject(idStr = "ckBoxSelect")
    CheckBox ckBoxSelect;

    @ViewInject(idStr = "ivUnReadDot")
    ImageView ivUnReadDot;

    @ViewInject(idStr = "ivRecommendCover")
    ImageView ivRecommendCover;

    @Override
    public void onBindData(View convertView, final RecommendBook item, int position) {
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(TimeFormatUtil.getDescriptionTimeFromDateString(item.updated))) {
            latelyUpdate = TimeFormatUtil.getDescriptionTimeFromDateString(item.updated) + ":";
            tvLatelyUpdate.setText(latelyUpdate);
        }
        tvRecommendTitle.setText(item.title);
        tvRecommendShort.setText(item.lastChapter);
        ivTopLabel.setVisibility(item.isTop?View.VISIBLE:View.GONE);
        ckBoxSelect.setVisibility(item.showCheckBox?View.VISIBLE:View.GONE);
        ckBoxSelect.setChecked(item.isSeleted);
        ckBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.isSeleted = isChecked;
            }
        });
        ivUnReadDot.setVisibility(TimeFormatUtil.formatZhuiShuDateString(item.updated)
                .compareTo(item.recentReadingTime) > 0?View.VISIBLE:View.GONE);
        showImage(item,ivRecommendCover);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadActivity.launch(getContext(),item,true);
            }
        });
    }


    private void showImage(RecommendBook item,ImageView ivRecommendCover){
        if (item.path != null && item.path.endsWith(NovelConstans.SUFFIX_PDF)) {
            ivRecommendCover.setImageResource(R.drawable.ic_shelf_pdf);
        } else if (item.path != null && item.path.endsWith(NovelConstans.SUFFIX_EPUB)) {
            ivRecommendCover.setImageResource(R.drawable.ic_shelf_epub);
        } else if (item.path != null && item.path.endsWith(NovelConstans.SUFFIX_CHM)) {
            ivRecommendCover.setImageResource(R.drawable.ic_shelf_chm);
        } else if (item.isFromSD) {
            ivRecommendCover.setImageResource(R.drawable.ic_shelf_txt);
            long fileLen = NovelUtils.getChapterFile(item._id, 1).length();
            if (fileLen > 10) {
                /*double progress = ((double) SettingManager.getInstance().getReadProgress(item._id)[2]) / fileLen;
                NumberFormat fmt = NumberFormat.getPercentInstance();
                fmt.setMaximumFractionDigits(2);
                holder.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress));*/
            }
        } /*else if (!SettingManager.getInstance().isNoneCover()) {
            holder.setRoundImageUrl(R.id.ivRecommendCover, NovelConstans.IMG_BASE_URL + item.cover,
                    R.drawable.cover_default);
        }*/ else {
            ivRecommendCover.setImageResource(R.drawable.cover_default);
        }
    }
}
