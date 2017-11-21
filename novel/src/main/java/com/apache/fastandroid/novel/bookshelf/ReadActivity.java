package com.apache.fastandroid.novel.bookshelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.BookMixAToc;
import com.apache.fastandroid.novel.find.bean.ChapterRead;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.mananger.CacheManager;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.apache.fastandroid.novel.view.readview.BaseReadView;
import com.apache.fastandroid.novel.view.readview.OnReadStateChangeListener;
import com.apache.fastandroid.novel.view.readview.PageWidget;
import com.tesla.framework.common.util.sp.SPUtils;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/4.
 */

public class ReadActivity extends BaseActivity {


    private RecommendBook mBook;
    private boolean isFromSdCard;


    private String bookId;
    @ViewInject(idStr = "flReadWidget")
    FrameLayout flReadWidget;

    private BaseReadView mPageWidget;
    private int curTheme = -1;


    public static void launch(Activity from,RecommendBook recommendBook,boolean isFromSdCard) {
        Intent intent = new Intent(from,ReadActivity.class);
        intent.putExtra("book", recommendBook);
        intent.putExtra("isFromSdCard",isFromSdCard);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.novel_activitty_detail);

        if (savedInstanceState == null){
            mBook = (RecommendBook) getIntent().getSerializableExtra("book");
            isFromSdCard = getIntent().getBooleanExtra("isFromSdCard",false);
        }else {
            mBook = (RecommendBook) savedInstanceState.getSerializable("book");
            isFromSdCard = savedInstanceState.getBoolean("isFromSdCard",false);
        }
        bookId = mBook._id;
        new LoadDataTask().execute();
        initPagerWidget();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("book", mBook);
        outState.putBoolean("isFromSdCard",isFromSdCard);
    }




    class LoadDataTask extends WorkTask<Void,Void,BookMixAToc>{

        @Override
        public BookMixAToc workInBackground(Void... params) throws TaskException {
            BookMixAToc bean =  NovelSdk.getInstance().getBookMixAToc(bookId,"chapters");
            return bean;
        }

        @Override
        protected void onSuccess(BookMixAToc bookMixAToc) {
            super.onSuccess(bookMixAToc);
            if (bookMixAToc != null && bookMixAToc.mixToc != null){
                showBookToc(bookMixAToc.mixToc.chapters);
            }
        }
    }
    private List<BookMixAToc.mixToc.Chapters> mChapterList = new ArrayList<>();

    private void initPagerWidget() {
        if (SPUtils.getInt(NovelConstans.FLIP_STYLE, 0) == 0) {
            mPageWidget = new PageWidget(this, bookId, mChapterList, new ReadListener());
        } else {
            //mPageWidget = new OverlappedWidget(this, bookId, mChapterList, new ReadListener());
        }
        flReadWidget.removeAllViews();
        flReadWidget.addView(mPageWidget);
    }


    /**
     * 获取当前章节。章节文件存在则直接阅读，不存在则请求
     */
    public void readCurrentChapter() {
        if (CacheManager.getInstance().getChapterFile(bookId, currentChapter) != null) {
            showChapterRead(null, currentChapter);
        } else {
            //mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
        }
    }


    /**
     * 加载章节列表
     *
     * @param list
     */
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {
        mChapterList.clear();
        mChapterList.addAll(list);

        readCurrentChapter();
    }
    /**
     * 是否开始阅读章节
     **/
    private boolean startRead = false;
    private int currentChapter = 1;
    public synchronized void showChapterRead(ChapterRead.Chapter data, int chapter) { // 加载章节内容
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(bookId, chapter, data);
        }

        if (!startRead) {
            startRead = true;
            currentChapter = chapter;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
            //hideDialog();
        }
    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
           /* NovelLog.i("onChapterChanged:" + chapter);
            currentChapter = chapter;
            mTocListAdapter.setCurrentChapter(currentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(bookId, i) == null) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }*/
        }

        @Override
        public void onPageChanged(int chapter, int page) {
            NovelLog.i("onPageChanged:" + chapter + "-" + page);
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            NovelLog.i("onLoadChapterFailure:" + chapter);
//            startRead = false;
//            if (CacheManager.getInstance().getChapterFile(bookId, chapter) == null)
//                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            NovelLog.i("onCenterClick");
            //toggleReadBar();
        }

        @Override
        public void onFlip() {
            //hideReadBar();
        }
    }
}
