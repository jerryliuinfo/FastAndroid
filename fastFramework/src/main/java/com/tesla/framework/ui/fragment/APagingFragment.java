package com.tesla.framework.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.tesla.framework.R;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.sp.SPUtil;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.adpater.IPagingAdapter;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.footer.AFooterItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.footer.BaisicFooterVIew;
import com.tesla.framework.ui.fragment.itemview.footer.OnFootViewListener;
import com.tesla.framework.ui.fragment.itemview.header.AHeaderItemViewCreator;
import com.tesla.framework.ui.widget.CustomToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerryliu on 2017/3/28.
 */

public abstract class APagingFragment<T extends Serializable,Ts extends Serializable, Header extends Serializable,V extends ViewGroup> extends ABaseFragment
        implements CustomToolbar.OnToolbarDoubleClickListener,AFooterItemViewHolder.OnFooterViewCallback,OnFootViewListener {

    public static final String TAG = "AFragment-Paging";

    public static final String PAGING_TASK_ID = "org.tesla.android.PAGING_TASK";

    private static final String SAVED_DATAS = "org.tesla.android.ui.Datas";
    private static final String SAVED_PAGING = "org.tesla.android.ui.Paging";
    private static final String SAVED_CONFIG = "org.tesla.android.ui.Config";

    private IPaging mPaging;
    private RefreshConfig refreshConfig;
    private IPagingAdapter<T> mAdapter;
    private AHeaderItemViewCreator<Header> mHeaderItemViewCreator;


    private IItemViewCreator<T> mFooterItemViewCreator;
    private AFooterItemViewHolder<T> mFooterItemView;
    //test


    public enum RefreshMode {
        /**
         * 重设数据
         */
        reset,
        /**
         * 上拉，加载更多
         */
        update,
        /**
         * 下拉，刷新最新
         */
        refresh
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            refreshConfig = new RefreshConfig();
        }else {
            refreshConfig = (RefreshConfig) savedInstanceState.getSerializable(SAVED_CONFIG);
        }
        if (savedInstanceState != null && savedInstanceState.getSerializable(SAVED_PAGING) != null){
            mPaging = (IPaging) savedInstanceState.getSerializable(SAVED_PAGING);
        }else {
            mPaging = newPaging();
        }
        ArrayList<T> datas = (ArrayList<T>) (savedInstanceState == null ? new ArrayList<>():savedInstanceState.getSerializable(SAVED_DATAS));

        mAdapter = newAdapter(datas);


        super.onCreate(savedInstanceState);


    }

    @Override
    void _layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super._layoutInit(inflater, savedInstanceSate);
        setUpRefreshConfig(getRefreshConfig());
        setUpRefreshView();
        setUpRefreshViewWithConfig(getRefreshConfig());
        bindAdapter(getAdapter());
        //setupDragMove();
    }

    @Override
    public void requestData() {
        RefreshMode mode = RefreshMode.reset;
        // 如果没有Loading视图，且数据为空，就显示FootView加载状态
        if (getAdapter().getDatas().size() == 0 && loadingLayout == null)
            mode = RefreshMode.update;
        requestData(mode);
    }

    @Override
    public void requestDataOutofdate() {
        super.requestDataOutofdate();
        putLastReadPosition(0);
        putLastReadTop(0);
        requestDataSetRefreshing();
    }

    public void requestData(RefreshMode mode){

    }

    protected void setUpRefreshConfig(RefreshConfig refreshConfig){

    }

    protected void setUpRefreshView(){
        mHeaderItemViewCreator = configHeadItemViewCreator();
        if (mHeaderItemViewCreator != null){
            addHeaderViewToRefreshView(mHeaderItemViewCreator);
        }
        if (refreshConfig != null && refreshConfig.footerMoreEnable){
            mFooterItemViewCreator = configFooterItemViewCreator();
            View convertView = mFooterItemViewCreator.newContentView(getActivity().getLayoutInflater(),null,IPagingAdapter.TYPE_FOOTER);
            mFooterItemView = (AFooterItemViewHolder<T>) mFooterItemViewCreator.newItemView(convertView,IPagingAdapter.TYPE_FOOTER);
        }
        if (mFooterItemViewCreator != null){
            addFooterViewToRefreshView(mFooterItemView);
        }

    }


    protected void setUpRefreshViewWithConfig(RefreshConfig refreshConfig){

    }
    /**
     * 设置列表控件状态为刷新结束,可以做一些收尾工作，例如SwipeRefreshLayout可以设置
     * 消失了
     * @param mode
     */
    protected void onRefreshViewFinished(RefreshMode mode){

    }



    protected void addHeaderViewToRefreshView(AHeaderItemViewCreator<Header> headerItemViewCreator){

    }

    protected void addFooterViewToRefreshView(IITemView<T> footerItemView){

    }

    protected AHeaderItemViewCreator<Header> configHeadItemViewCreator(){
        return null;
    }

    protected IItemViewCreator<T> configFooterItemViewCreator(){
        return new IItemViewCreator<T>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(BaisicFooterVIew.LAY_RES, parent,false);
            }

            @Override
            public IITemView<T> newItemView(View contentView, int viewType) {
                return new BaisicFooterVIew<>(getActivity(), contentView,APagingFragment.this);
            }
        };
    }

    public abstract IItemViewCreator<T> configItemViewCreator();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_CONFIG, refreshConfig);
        if (mPaging != null){
            outState.putSerializable(SAVED_PAGING, mPaging);
        }
        onSaveState(outState);
    }

    private void onSaveState(Bundle outState){
        if (getAdapter() != null && getAdapter().getDatas().size() > 0){
            outState.putSerializable(SAVED_DATAS, (Serializable) getAdapter().getDatas());
        }
    }



    public IPagingAdapter<T> getAdapter() {
        return mAdapter;
    }

    public List<T> getAdapterItems(){
        return getAdapter().getDatas();
    }

    public void setAdapterItems(List<T> datas){
        getAdapterItems().clear();
        getAdapterItems().addAll(datas);
    }




    /**
     * 配置Adapter
     *
     * @param datas
     * @return
     */
    protected abstract IPagingAdapter<T> newAdapter(ArrayList<T> datas);

    protected abstract void bindAdapter(IPagingAdapter<T> adpater);

    /**
     * 设置分页
     *
     * @return <tt>null</tt> 不分页
     */
    protected IPaging<T, Ts> newPaging() {
        return new IndexPaging<>();
    }


    public RefreshConfig getRefreshConfig() {
        return refreshConfig;
    }



    public abstract V getRefreshView();


    @Override
    public boolean canLoadMore() {
        return refreshConfig != null && !refreshConfig.pagingEnd;
    }


    @Override
    public void onLoadMore() {
        if (canLoadMore()){
            onPullUpToRefresh();
        }
    }

    /**
     * 下拉刷新
     */
    protected void onPullDownToRefresh(){
        requestData(RefreshMode.refresh);
    }

    /**
     * 上拉刷新
     */
    protected void onPullUpToRefresh(){
        requestData(RefreshMode.update);
    }


    @Override
    public boolean OnToolbarDoubleClick() {
        return false;
    }



    /**
     * 设置刷新控件为刷新状态且刷新数据
     *
     */
    public void requestDataSetRefreshing() {
        // 如果没有正在刷新，设置刷新控件，且子类没有自动刷新
        if (!isRefreshing() && !setRefreshViewToLoading())
            requestData(RefreshMode.reset);
    }

    /**
     * 延迟刷新
     * @param delay
     */
    public void requestDataDelaySetRefreshing(long delay) {
        Runnable requestDelayRunnable = new Runnable() {
            public void run() {
                FastLog.d(TAG, "延迟刷新，开始刷新, " + this.toString());
                APagingFragment.this.requestDataSetRefreshing();
            }
        };
        this.runUIRunnable(requestDelayRunnable, delay);
    }

    /**
     * 设置列表控件状态为刷新状态
     *
     * @return true:某些控件，设置它的刷新状态，它会自己自动回调Callback去刷新数据，true即这种情况
     */
    public boolean setRefreshViewToLoading() {
        return false;
    }

    /**
     * 当前页面是否正在加载刷新
     *
     * @return
     */
    public boolean isRefreshing() {
        return mPagingTask != null;
    }

    APagingTask mPagingTask;

    /**
     * 分页线程，根据{@link IPaging}构造的分页参数列表调用接口
     *
     * @author JerryLiu
     *
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     */
    public abstract class APagingTask<Params, Progress, Result extends Serializable> extends ABaseTask<Params, Progress, Result> {

        protected final RefreshMode mode;

        public APagingTask(RefreshMode mode) {
            super(PAGING_TASK_ID);
            this.mode = mode;
            mPagingTask = this;

            //这里为啥需要重新设置分页对象?
            if (mode == RefreshMode.reset && mPaging != null)
                mPaging = newPaging();
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();

            FastLog.d(TAG, toString() + "-" + ABaseTaskState.prepare + " - " + mode);
            onTaskStateChanged(ABaseTaskState.prepare, null, mode);
        }

        @Override
        public Result workInBackground(Params... params) throws TaskException {
            String previousPage = null;
            String nextPage = null;

            if (mPaging != null) {
                previousPage = mPaging.getPreviousPage();
                nextPage = mPaging.getNextPage();
            }

            return workInBackground(mode, previousPage, nextPage, params);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onSuccess(Result result) {
            if (result == null || getActivity() == null) {
                super.onSuccess(result);
                return;
            }

            bindAdapter(getAdapter());

            List<T> resultList;
            if (result instanceof List)
                resultList = (List<T>) result;
            else {
                resultList = parseResult(result);
                if (resultList == null)
                    resultList = new ArrayList<>();
            }

            // 如果子类没有处理新获取的数据刷新UI，默认替换所有数据
            if (!handleResult(mode, resultList))
                if (mode == RefreshMode.reset)
                    setAdapterItems(new ArrayList<T>());

            // append数据
            if (mode == RefreshMode.reset || mode == RefreshMode.refresh)
                IPagingAdapter.Utils.addItemsAtFrontAndRefresh(getAdapter(), resultList);
            else if (mode == RefreshMode.update)
                IPagingAdapter.Utils.addItemsAndRefresh(getAdapter(), resultList);

            // 处理分页数据
            if (mPaging != null) {
                if (getAdapter() != null && getAdapter().getDatas().size() != 0)
                    mPaging.processData(result,  getAdapter().getDatas().get(0),
                             getAdapter().getDatas().get(getAdapter().getDatas().size() - 1));
                else
                    mPaging.processData(result, null, null);
            }

            // 如果是重置数据，重置canLoadMore
            if (mode == RefreshMode.reset)
                refreshConfig.pagingEnd = false;
            // 如果数据少于这个值，默认加载完了
            if (mode == RefreshMode.update || mode == RefreshMode.reset){
                //拉取到的数据小于分页大小 则认为没有更多数据了
                refreshConfig.pagingEnd = resultList.size() < refreshConfig.pageSize;
            }
            // 如果是缓存数据，且已经过期
            if (result instanceof IResult) {
                // 这里增加一个自动刷新设置功能
                IResult iResult = (IResult) result;

                if (iResult.isFromCache() && !iResult.isOutOfData())
                    toLastReadPosition();

                if (mode == RefreshMode.reset || mode == RefreshMode.update) {
                    if (iResult.endPaging())
                        refreshConfig.pagingEnd = true;
                    else
                        refreshConfig.pagingEnd = false;
                }
            }

            if (mode == RefreshMode.reset && getTaskCount(getTaskId()) > 1)
                getAdapter().notifyDataSetChanged();

            setUpRefreshViewWithConfig(refreshConfig);

            FastLog.d(TAG, toString() + "-" + ABaseTaskState.success + " - " + mode);
            onTaskStateChanged(ABaseTaskState.success, null, mode);

            super.onSuccess(result);
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);

            FastLog.d(TAG, toString() + "-" + ABaseTaskState.falid + " - " + mode + "-" + exception.getMessage());
            onTaskStateChanged(ABaseTaskState.falid, exception, mode);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            FastLog.d(TAG, toString() + "-" + ABaseTaskState.canceled + " - " + mode);
            onTaskStateChanged(ABaseTaskState.canceled, null, mode);
        }

        @Override
        protected void onFinished() {
            super.onFinished();

            FastLog.d(TAG, toString() + "-" + ABaseTaskState.finished + " - " + mode);
            onTaskStateChanged(ABaseTaskState.finished, null, mode);

            mPagingTask = null;
        }

        /**
         * 每次调用接口，获取新的数据时调用这个方法
         *
         * @param mode
         *            当次拉取数据的类型
         * @param datas
         *            当次拉取的数据
         * @return <tt>false</tt> 如果mode={@link RefreshMode#reset}
         *         默认清空adapter中的数据
         */
        protected boolean handleResult(RefreshMode mode, List<T> datas) {
            return false;
        }

        /**
         * 将Ts转换成List(T)
         *
         * @param result
         *            List(T)
         * @return
         */
         protected abstract List<T> parseResult(Result result);

        /**
         * 异步执行方法
         *
         * @param mode 刷新模式
         * @param previousPage 上一页页码
         * @param nextPage 下一页页码
         * @param params task参数
         * @return
         * @throws TaskException
         */
         protected abstract Result workInBackground(RefreshMode mode, String previousPage, String nextPage, Params... params) throws TaskException;

    }

    private boolean isViewScrolling = false;// 正在滚动

    /**
     * 默认不管你怎么滑动ListView，都会去加载图片。但是如果是图片非常多，滑动非常快的时候，可以把这个属性关了，
     * 那么滑动ListView的时候，不会加载图片，只有在停止滑动到时候，才加载图片
     * @param scrollState
     */
    protected void onScrollStateChanged(int scrollState) {
        // 滑动的时候，不加载图片
        if (!refreshConfig.displayWhenScrolling) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                isViewScrolling = true;
            }
            else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isViewScrolling = true;
            }
            else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                isViewScrolling = false;
            }
        }

        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && // 停止滚动
                refreshConfig.footerMoreEnable && // 自动加载更多
                mFooterItemView != null &&// 配置了FooterView
                !refreshConfig.pagingEnd  // 分页未加载完
                ) {
            int childCount = getRefreshView().getChildCount();
            if (childCount > 0 && getRefreshView().getChildAt(childCount - 1) == mFooterItemView.getConvertView()) {
                setFooterViewToRefreshing();
            }
        }
        int childCount = getRefreshView().getChildCount();
        if (mFooterItemView != null && childCount > 0 && getRefreshView().getChildAt(childCount - 1) == mFooterItemView.getConvertView()) {
            setFooterViewToRefreshing();
        }

        // 保存最后浏览位置
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (!TextUtils.isEmpty(refreshConfig.positionKey) && getRefreshView() != null) {
                int firstVisiblePostion = getFirstVisiblePosition();
                int top = getRefreshView().getChildAt(0).getTop();
                FastLog.d(TAG, "firstVisiblePostion = %s, top = %s", firstVisiblePostion, top);
                putLastReadPosition(firstVisiblePostion);

                putLastReadTop(getRefreshView().getChildAt(0).getTop());
            }
        }
    }

    /**
     * 子类需要根据不同的刷新模式，来刷新自己的UI
     *
     * @param state
     * @param exception
     * @param mode
     */
    protected void onTaskStateChanged(ABaseTaskState state, TaskException exception, RefreshMode mode) {
        super.onTaskStateChanged(state, exception);

        onTaskStateChanged(mFooterItemView, state, exception, mode);

        if (state == ABaseTaskState.success) {
            if (isContentEmpty()) {
                if (emptyLayout != null && !TextUtils.isEmpty(refreshConfig.emptyHint))
                    ViewUtils.setTextViewValue(emptyLayout, R.id.txtLoadEmpty, refreshConfig.emptyHint);
            }
        }
        else if (state == ABaseTaskState.falid) {
            if (isContentEmpty()) {
                if (loadFailureLayout != null && !TextUtils.isEmpty(exception.getMessage()))
                    ViewUtils.setTextViewValue(loadFailureLayout, R.id.txtLoadFailed, exception.getMessage());
            }
        }
        else if (state == ABaseTaskState.finished) {
            onRefreshViewFinished(mode);
        }
    }

    /**
     * footerview刷新
     * @param footerItemViewHolder
     * @param state
     * @param exception
     * @param mode
     */
    @Override
    public void onTaskStateChanged(AFooterItemViewHolder<?> footerItemViewHolder,ABaseTaskState state, TaskException exception, RefreshMode mode){
        if (refreshConfig == null || !refreshConfig.footerMoreEnable || footerItemViewHolder == null){
            return;
        }
        footerItemViewHolder.onTaskStateChanged(footerItemViewHolder, state, exception, mode);
    }

    @Override
    public void setFooterViewToRefreshing() {
        if (mFooterItemView != null){
            mFooterItemView.setFooterViewToRefreshing();
        }
    }

    @Override
    public boolean isContentEmpty() {
        return getAdapter() == null || getAdapter().getDatas().size() == 0;
    }


    /**
     * 是否显示图片接口实现
     */
    @Override
    public boolean canDisplay() {
        if (getRefreshConfig().displayWhenScrolling){
            return true;
        }
        return isViewScrolling;
    }
    /**
     * 滑动到最后阅读的位置
     */
    protected void toLastReadPosition() {

    }

    protected int getLastReadPosition() {
        return SPUtil.getInt(refreshConfig.positionKey + "Position", 0);
    }

    protected void putLastReadPosition(int position) {
        if (!TextUtils.isEmpty(refreshConfig.positionKey)){
            SPUtil.putInt(refreshConfig.positionKey + "Position", position);
        }
    }

    protected int getLastReadTop() {
        return SPUtil.getInt(refreshConfig.positionKey + "Top", 0);
    }

    protected void putLastReadTop(int top) {
        if (!TextUtils.isEmpty(refreshConfig.positionKey)){
            SPUtil.putInt(refreshConfig.positionKey + "Top", top);
        }
    }


    protected int getFirstVisiblePosition() {
        return 0;
    }
    
    protected void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // 重构需要实现的方法
    public void refreshUI() {

    }

    public void releaseImageViewByIds() {

    }

    protected void setupDragMove(){

    }
}
