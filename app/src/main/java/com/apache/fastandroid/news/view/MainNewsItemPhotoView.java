package com.apache.fastandroid.news.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.NewsSummary;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.dimen.DimensUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsItemPhotoView extends ARecycleViewItemViewHolder<NewsSummary> {

    public static final int LAY_RES = R.layout.item_news_photo;

    @ViewInject(idStr = "news_summary_title_tv")
    TextView news_summary_title_tv;

    @ViewInject(idStr = "news_summary_ptime_tv")
    TextView news_summary_ptime_tv;


    @ViewInject(idStr = "news_summary_photo_iv_group")
    LinearLayout news_summary_photo_iv_group;


    @ViewInject(idStr = "news_summary_photo_iv_left")
    ImageView news_summary_photo_iv_left;

    @ViewInject(idStr = "news_summary_photo_iv_middle")
    ImageView news_summary_photo_iv_middle;

    @ViewInject(idStr = "news_summary_photo_iv_right")
    ImageView news_summary_photo_iv_right;





    public MainNewsItemPhotoView(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindData(View convertView, NewsSummary newsSummary, int position) {
       /* String title = newsSummary.getTitle();
        String ptime = newsSummary.getPtime();
        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);*/
        news_summary_title_tv.setText(newsSummary.title);
        news_summary_ptime_tv.setText(newsSummary.ptime);
        setImageView(newsSummary);
    }


    private void setImageView( NewsSummary newsSummary) {
        int PhotoThreeHeight = (int) DimensUtil.dp2px(FrameworkApplication.getContext(),90);
        int PhotoTwoHeight = (int) DimensUtil.dp2px(FrameworkApplication.getContext(),120);
        int PhotoOneHeight = (int)DimensUtil.dp2px(FrameworkApplication.getContext(),150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;
        ViewGroup.LayoutParams layoutParams = news_summary_photo_iv_group.getLayoutParams();

        if (newsSummary.ads != null) {
            List<NewsSummary.AdsBean> adsBeanList = newsSummary.ads;
            int size = adsBeanList.size();
            if (size >= 3) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                imgSrcRight = adsBeanList.get(2).getImgsrc();
                layoutParams.height = PhotoThreeHeight;
//                holder.setText(R.id.news_summary_title_tv, AppApplication.getAppContext()
//                        .getString(R.string.photo_collections, adsBeanList.get(0).getTitle()));

                news_summary_title_tv.setText(String.format(ResUtil.getString(R.string.photo_collections),adsBeanList.get(0).getTitle() ));
            } else if (size >= 2) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else if (newsSummary.imgextra != null) {
            int size = newsSummary.imgextra.size();
            if (size >= 3) {
                imgSrcLeft = newsSummary.imgextra.get(0).getImgsrc();
                imgSrcMiddle = newsSummary.imgextra.get(1).getImgsrc();
                imgSrcRight = newsSummary.imgextra.get(2).getImgsrc();

                layoutParams.height = PhotoThreeHeight;
            } else if (size >= 2) {
                imgSrcLeft = newsSummary.imgextra.get(0).getImgsrc();
                imgSrcMiddle = newsSummary.imgextra.get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsSummary.imgextra.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsSummary.imgsrc;

            layoutParams.height = PhotoOneHeight;
        }

        setPhotoImageView(imgSrcLeft, imgSrcMiddle, imgSrcRight);
        news_summary_photo_iv_group.setLayoutParams(layoutParams);
    }


    private void setPhotoImageView(String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            news_summary_photo_iv_left.setVisibility(View.VISIBLE);
            ImageLoaderManager.getInstance().showImage(news_summary_photo_iv_left,imgSrcLeft,getContext());
        } else {
            news_summary_photo_iv_left.setVisibility(View.GONE);
        }

        if (imgSrcMiddle != null) {
            news_summary_photo_iv_middle.setVisibility(View.VISIBLE);
            ImageLoaderManager.getInstance().showImage(news_summary_photo_iv_middle,imgSrcMiddle,getContext());
        } else {
            news_summary_photo_iv_middle.setVisibility(View.GONE);
        }

        if (imgSrcRight != null) {
            news_summary_photo_iv_right.setVisibility(View.VISIBLE);
            ImageLoaderManager.getInstance().showImage(news_summary_photo_iv_right,imgSrcLeft,getContext());
        } else {
            news_summary_photo_iv_right.setVisibility(View.GONE);
        }
    }
}
