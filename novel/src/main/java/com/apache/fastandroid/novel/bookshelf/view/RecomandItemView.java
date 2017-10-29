package com.apache.fastandroid.novel.bookshelf.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.util.NovelUtils;
import com.tesla.framework.common.util.TimeFormatUtil;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecomandItemView extends ARecycleViewItemViewHolder<RecommendBook> {

    public RecomandItemView(Activity context, View itemView) {
        super(context, itemView);
    }



  /*  String latelyUpdate = "";
                if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
        latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
    }

                holder.setText(R.id.tvRecommendTitle, item.title)
            .setText(R.id.tvLatelyUpdate, latelyUpdate)
                        .setText(R.id.tvRecommendShort, item.lastChapter)
                        .setVisible(R.id.ivTopLabel, item.isTop)
                        .setVisible(R.id.ckBoxSelect, item.showCheckBox)
                        .setVisible(R.id.ivUnReadDot, FormatUtils.formatZhuiShuDateString(item.updated)
                                .compareTo(item.recentReadingTime) > 0);

                if (item.path != null && item.path.endsWith(Constant.SUFFIX_PDF)) {
        holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_pdf);
    } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_EPUB)) {
        holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_epub);
    } else if (item.path != null && item.path.endsWith(Constant.SUFFIX_CHM)) {
        holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_chm);
    } else if (item.isFromSD) {
        holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_txt);
        long fileLen = FileUtils.getChapterFile(item._id, 1).length();
        if (fileLen > 10) {
            double progress = ((double) SettingManager.getInstance().getReadProgress(item._id)[2]) / fileLen;
            NumberFormat fmt = NumberFormat.getPercentInstance();
            fmt.setMaximumFractionDigits(2);
            holder.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress));
        }
    } else if (!SettingManager.getInstance().isNoneCover()) {
        holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.cover,
                R.drawable.cover_default);
    } else {
        holder.setImageResource(R.id.ivRecommendCover, R.drawable.cover_default);
    }

    CheckBox ckBoxSelect = holder.getView(R.id.ckBoxSelect);
                ckBoxSelect.setChecked(item.isSeleted);
                ckBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
        boolean isChecked) {
            item.isSeleted = isChecked;
        }
    });
}
    */
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
