package com.apache.fastandroid.novel.find.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.BookDetail;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.EventManager;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.sqlite.CollectionDB;
import com.apache.fastandroid.novel.support.util.FormatTimeUtil;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.apache.fastandroid.novel.view.DrawableCenterButton;
import com.apache.fastandroid.novel.view.TagColor;
import com.apache.fastandroid.novel.view.TagGroup;
import com.tesla.framework.common.util.format.FormatUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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


    @ViewInject(idStr = "tag_group")
    TagGroup tag_group;

    @ViewInject(idStr = "tvlongIntro")
    TextView tvlongIntro;

    @ViewInject(idStr = "btn_JoinCollection")
    DrawableCenterButton btn_JoinCollection;



    public static void launch(Activity from,String bookId) {
        Intent intent = new Intent(from,BookDetailActivity.class);
        intent.putExtra("bookId",bookId);
        from.startActivity(intent);
    }

    private RecommendBook mRecommendBook;
    private boolean isCollected =false; //是否是收藏状态

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


        isCollected = CollectionDB.isCollected(mBookId);
        refreshCollectionIcon();

        btn_JoinCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecommendBook != null){
                    //如果当前为已经加入收藏状态，则取消收藏,否则加入收藏
                    isCollected =  !isCollected;
                    removeOrAddFromBookSelf();
                    refreshCollectionIcon();
                    //发广播通知界面刷新
                    EventManager.refreshCollectionList();

                }
            }
        });


    }


    private void removeOrAddFromBookSelf(){
        //加入书架
        if (isCollected){
            showMessage(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_has_joined_the_book_shelf),mRecommendBook.title));
            CollectionDB.insert(mRecommendBook);
        }
        //从书架移除
        else {
            CollectionDB.remove(mBookId);
            showMessage(FormatUtil.formatString(ResUtil.getString(R.string.book_detail_has_remove_the_book_shelf),mRecommendBook.title));
        }
    }



    private void refreshCollectionIcon(){
        if (isCollected){
            btn_JoinCollection.setText(R.string.book_detail_remove_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_del_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btn_JoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed));
            btn_JoinCollection.setCompoundDrawables(drawable, null, null, null);
            btn_JoinCollection.postInvalidate();
        }else {
            btn_JoinCollection.setText(R.string.book_detail_join_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_add_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btn_JoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_common_btn_solid_normal));
            btn_JoinCollection.setCompoundDrawables(drawable, null, null, null);
            btn_JoinCollection.postInvalidate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookId",mBookId);
    }




    class LoadBookDetailTask extends WorkTask<Void,Void,BookDetail>{

        @Override
        public BookDetail workInBackground(Void... params) throws TaskException {
            return NovelSdk.getInstance().getBookDetail(mBookId);
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


        tvlongIntro.setText(data.longIntro);

        mRecommendBook = new RecommendBook();
        mRecommendBook.title = data.title;
        mRecommendBook._id = data._id;
        mRecommendBook.cover = data.cover;
        mRecommendBook.lastChapter = data.lastChapter;
        mRecommendBook.updated = data.updated;

        showHotWord(data);

    }


    /**
     * 每次显示8个
     */
    private void showHotWord(BookDetail data) {
        List<String> tagList = new ArrayList<>();
        int times = 0;
        tagList.addAll(data.tags);
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            tag_group.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
    }
}
