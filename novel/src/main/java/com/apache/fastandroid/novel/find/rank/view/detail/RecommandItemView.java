package com.apache.fastandroid.novel.find.rank.view.detail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.common.util.FormatUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.component.imageloader.ImageLoaderOptions;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;


/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecommandItemView extends ARecycleViewItemViewHolder<RecommendBook> {

    public static final int LAY_RES = R.layout.novel_item_book_detail_recommend_book_list;

    @ViewInject(idStr = "ivBookListCover")
    ImageView ivBookListCover;

    @ViewInject(idStr = "tvBookListTitle")
    TextView tvBookListTitle;

    @ViewInject(idStr = "tvBookAuthor")
    TextView tvBookAuthor;

    @ViewInject(idStr = "tvBookListDesc")
    TextView tvBookListDesc;

    @ViewInject(idStr = "tvBookCount")
    TextView tvBookCount;

    @ViewInject(idStr = "tvCollectorCount")
    TextView tvCollectorCount;

    public RecommandItemView(Activity context, View itemView) {
        super(context, itemView);
    }
   /* holder.setText(R.id.tvBookListTitle, item.title)
            .setText(R.id.tvBookAuthor, item.author)
                .setText(R.id.tvBookListTitle, item.title)
                .setText(R.id.tvBookListDesc, item.desc)
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
            .book_detail_recommend_book_list_book_count), item.bookCount))
            .setText(R.id.tvCollectorCount, String.format(mContext.getString(R.string
            .book_detail_recommend_book_list_collector_count), item.collectorCount));
        holder.setOnItemViewClickListener(new NoDoubleClickListener() {
        @Override
        protected void onNoDoubleClick(View view) {
            itemClickListener.onItemClick(holder.getItemView(), position, item);
        }
    });*/

    @Override
    public void onBindData(View convertView, RecommendBook item, int position) {
        tvBookListTitle.setText(item.title);
        tvBookAuthor.setText(item.author);
        tvBookListDesc.setText(item.desc);

        tvBookCount.setText(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_recommend_book_list_book_count),item.bookCount));
        tvCollectorCount.setText(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_recommend_book_list_collector_count),item.collectorCount));

        if (!TextUtils.isEmpty(item.cover)){
            ImageLoaderManager.getInstance().showImage(new ImageLoaderOptions.Builder(ivBookListCover, NovelConstans.IMG_BASE_URL,getContext()).placeholder(R.drawable.cover_default).build());
            //ImageLoaderManager.getInstance().showImage(ivBookListCover, NovelConstans.IMG_BASE_URL,getContext());
        }
        //holder.setRoundImageUrl(R.id.ivBookListCover, Constant.IMG_BASE_URL + item.cover, R.drawable.cover_default);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
