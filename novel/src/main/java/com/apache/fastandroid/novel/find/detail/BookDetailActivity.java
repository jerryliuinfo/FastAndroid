package com.apache.fastandroid.novel.find.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.BookDetail;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.util.FormatTimeUtil;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.common.util.FormatUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/10/13.
 */

public class BookDetailActivity extends BaseActivity {

    private String mBookId;

    @ViewInject(idStr = "ivBookCover")
    ImageView ivBookCover;

    @ViewInject(idStr = "tvBookListTitle")
    TextView tvBookListTitle;

    @ViewInject(idStr = "tvBookListAuthor")
    TextView tvBookListAuthor;

    @ViewInject(idStr = "tvCatgory")
    TextView tvCatgory;

    @ViewInject(idStr = "tvWordCount")
    TextView tvWordCount;

    @ViewInject(idStr = "tvLatelyUpdate")
    TextView tvLatelyUpdate;

    @ViewInject(idStr = "tvLatelyFollower")
    TextView tvLatelyFollower;

    @ViewInject(idStr = "tvRetentionRatio")
    TextView tvRetentionRatio;

    @ViewInject(idStr = "tvSerializeWordCount")
    TextView tvSerializeWordCount;

    @ViewInject(idStr = "tvCommunity")
    TextView tvCommunity;

    @ViewInject(idStr = "tvHelpfulYes")
    TextView tvHelpfulYes;



    public static void launch(Activity from,String bookId) {
        Intent intent = new Intent(from,BookDetailActivity.class);
        intent.putExtra("bookId",bookId);
        from.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_activity_book_detail);
        if (savedInstanceState == null){
            mBookId = getIntent().getStringExtra("bookId");
        }else {
            mBookId = savedInstanceState.getString("bookId");
        }
        setToolbarTitle("书籍详情");

        getSupportFragmentManager().beginTransaction().replace(R.id.container_hot_review,
                HotReviewFragment.newFragment(mBookId)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_recommand_book_list,
                RecommandBooksFragment.newFragment(mBookId)).commit();
        new LoadBookDetailTask().execute();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookId",mBookId);
    }




    class LoadBookDetailTask extends WorkTask<Void,Void,BookDetail>{

        @Override
        public BookDetail workInBackground(Void... params) throws TaskException {
            return NovelSdk.newInstance().getBookDetail(mBookId);
        }

        @Override
        protected void onSuccess(BookDetail bookDetail) {
            super.onSuccess(bookDetail);
            NovelLog.d("bookDetail = %s",bookDetail);
            updateHeader(bookDetail);
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
        }

    }

    private void updateHeader(BookDetail data){
        ImageLoaderManager.getInstance().showImage(ivBookCover, NovelConstans.IMG_BASE_URL+data.cover,this);


        tvBookListTitle.setText(data.title);
        tvBookListAuthor.setText(String.format(getString(R.string.book_detail_author), data.author));
        tvCatgory.setText(String.format(getString(R.string.book_detail_category), data.cat));
        tvWordCount.setText(FormatTimeUtil.formatWordCount(data.wordCount));
        tvLatelyUpdate.setText(FormatTimeUtil.getDescriptionTimeFromDateString(data.updated));
        tvLatelyFollower.setText(String.valueOf(data.latelyFollower));
        /*tvRetentionRatio.setText(TextUtils.isEmpty(data.retentionRatio) ?
                "-" : String.format(getString(R.string.book_detail_retention_ratio),
                data.retentionRatio));*/
        tvRetentionRatio.setText(TextUtils.isEmpty(data.retentionRatio) ?
                "-" : FormatUtil.formatString(ResUtil.getString(R.string.book_detail_retention_ratio),data.retentionRatio));
        tvSerializeWordCount.setText(data.serializeWordCount < 0 ? "-" :
                String.valueOf(data.serializeWordCount));

        tvCommunity.setText(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_community),data.title));
        tvHelpfulYes.setText(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_post_count),data.postCount));


    }


}
