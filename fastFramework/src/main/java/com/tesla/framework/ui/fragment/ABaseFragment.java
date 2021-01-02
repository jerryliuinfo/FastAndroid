package com.tesla.framework.ui.fragment;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesla.framework.R;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.component.imageloader.BitmapOwner;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.task.ITaskManager;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.TaskManager;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.InjectUtility;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.ToastUtils;
import com.tesla.framework.ui.widget.swipeback.SwipeActivityHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

/**
 * 基于ABaseFragment，维护与Activity之间的生命周期绑定，管理WorkTask线程，支持四种个基本视图之间的自动切换<br/>
 *
 * 1、处理缓存数据过期后，自动刷新页面<br/>
 * 2、处理页面离开设定时间后，自动刷新页面<br/>
 *
 */
public abstract class ABaseFragment extends Fragment implements ITaskManager,SwipeActivityHelper.EnableSwipeback,BitmapOwner{

    public static final String TAG = "AFragment-Base";


    ViewGroup rootView;// 根视图

    @Nullable
    @ViewInject(idStr = "layoutLoading")
    public View loadingLayout;// 加载中视图
    @Nullable
    @ViewInject(idStr = "layoutLoadFailed")
    public View loadFailureLayout;// 加载失败视图
    @Nullable
    @ViewInject(idStr = "layoutContent")
    public View contentLayout;// 内容视图
    @Nullable
    @ViewInject(idStr = "layoutEmpty")
    public View emptyLayout;// 空视图


    // 标志是否ContentView是否为空, 默认是没有数据的
    private boolean contentEmpty = true;

    protected long lastResultGetTime = 0;// 最后一次非缓存数据获取时间

    private boolean destory = false;
    private TaskManager taskManager;// 管理线程

    public enum ABaseTaskState {
        prepare, falid, success, finished, canceled
    }




    // UI线程的Handler
    protected static Handler mHandler = new Handler(Looper.getMainLooper()) {

    };





    long startTime1 = 0;
    long startTime2 = 0;
    long startTime3 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameworkLogUtil.d("onCreate  --- >fragment: %s",this);
        startTime1 = System.currentTimeMillis();
        taskManager = new TaskManager();
        if (savedInstanceState != null) {
            taskManager.restore(savedInstanceState);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        startTime2 = System.currentTimeMillis();
        FrameworkLogUtil.d("onCreateView  --- >time diff: %s ms,fragment: %s", (startTime2 - startTime1),this);
        if (inflateContentView() > 0) {
            ViewGroup contentView = (ViewGroup) inflater.inflate(inflateContentView(), null);
            contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            setupContentView(inflater, contentView, savedInstanceState);

            return getContentView();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startTime3 = System.currentTimeMillis();
        initTitle(savedInstanceState);
//        FrameworkLogUtil.d("onViewCreated  --- >time diff: %s ms,fragment: %s", (startTime3 - startTime2),this);
        /**
         * 为了解决使用Kotlin-Android-Extensions 的试图绑定功能(不用findViewById), 在onCreateView中不能直接访问视图，
         * 因为视图没有加载完成，容易引起空指针，需要在onViewCreated中访问视图，所以把 _layoutInit  和 layoutInit 从
         * onCreateView 中移动到了 onViewCreated方法中
         *
         */
        //绑定视图
        _layoutInit(null, savedInstanceState);

        layoutInit(null, savedInstanceState);
    }

    /**
     * 根据ContentView初始化视图
     *
     * @param inflater
     * @param contentView
     * @param savedInstanceState
     */
    protected void setupContentView(LayoutInflater inflater, ViewGroup contentView, Bundle savedInstanceState) {
        setContentView(contentView);
//        //绑定视图
//        _layoutInit(inflater, savedInstanceState);
//
//        layoutInit(inflater, savedInstanceState);
    }

    public void setContentView(ViewGroup view) {
        this.rootView = view;
    }

    /**
     * 根视图
     *
     * @return
     */
    public ViewGroup getContentView() {
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            requestData();
        }
    }

    /**
     * Action的home被点击了
     *
     * @return
     */
    public boolean onHomeClick() {
        return onBackClick();
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }

    /**
     * 初次创建时默认会调用一次
     */
    public void requestData() {

    }


    /**
     * A*Fragment重写这个方法
     *
     * @param inflater
     * @param savedInstanceSate
     */
    void _layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        InjectUtility.initInjectedView(getActivity(), this, getContentView());

        if (emptyLayout != null) {
            View reloadView = emptyLayout.findViewById(R.id.layoutReload);
            if (reloadView != null) {
                setViewOnClick(reloadView);
            }
        }

        if (loadFailureLayout != null) {
            View reloadView = loadFailureLayout.findViewById(R.id.layoutReload);
            if (reloadView != null) {
                setViewOnClick(reloadView);
            }
        }

        setViewVisiable(loadingLayout, View.GONE);
        setViewVisiable(loadFailureLayout, View.GONE);
        setViewVisiable(emptyLayout, View.GONE);

        if (isContentEmpty()) {
            // 如果视图为空，就开始加载数据
            if (savedInstanceSate != null) {
                requestData();
            }
            else {
                //显示空视图
                setViewVisiable(emptyLayout, View.VISIBLE);
                setViewVisiable(contentLayout, View.GONE);
            }
        }
        else {
            setViewVisiable(contentLayout, View.VISIBLE);
        }
    }

    public View findViewById(int viewId) {
        if (getContentView() == null) {
            return null;
        }

        return getContentView().findViewById(viewId);
    }

    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }

    public boolean isContentEmpty() {
        return contentEmpty;
    }

    /**
     * 视图点击回调，子类重写
     *
     * @param view
     */
    public void onViewClicked(View view) {
        if (view.getId() == R.id.layoutReload){
            requestData();
        }
        else if (view.getId() == R.id.layoutRefresh){
            requestData();
        }
    }

    protected void setViewVisiable(View v, int visibility) {
        if (v != null && v.getVisibility() != visibility) {
            v.setVisibility(visibility);
        }
    }


    /**
     *
     */
    protected void onTaskStateChanged(ABaseTaskState state, TaskException exception) {

        // 开始Task
        if (state == ABaseTaskState.prepare) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.VISIBLE);

                setViewVisiable(contentLayout, View.GONE);
            }
            else {
                setViewVisiable(loadingLayout, View.GONE);

                setViewVisiable(contentLayout, View.VISIBLE);
            }

            setViewVisiable(emptyLayout, View.GONE);
            if (isContentEmpty() && loadingLayout == null) {
                setViewVisiable(contentLayout, View.VISIBLE);
            }

            setViewVisiable(loadFailureLayout, View.GONE);
        }
        // Task成功
        else if (state == ABaseTaskState.success) {
            setViewVisiable(loadingLayout, View.GONE);

            if (isContentEmpty()) {
                setViewVisiable(emptyLayout, View.VISIBLE);
                setViewVisiable(contentLayout, View.GONE);
            }
            else {
                setViewVisiable(contentLayout, View.VISIBLE);
                setViewVisiable(emptyLayout, View.GONE);
            }
        }
        // 取消Task
        else if (state == ABaseTaskState.canceled) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.GONE);
                setViewVisiable(emptyLayout, View.VISIBLE);
            }
        }
        // Task失败
        else if (state == ABaseTaskState.falid) {
            if (isContentEmpty()) {
                if (loadFailureLayout != null) {
                    setViewVisiable(loadFailureLayout, View.VISIBLE);

                    if (exception != null) {
                        TextView txtLoadFailed = (TextView) loadFailureLayout.findViewById(R.id.txtLoadFailed);
                        if (txtLoadFailed != null) {
                            txtLoadFailed.setText(exception.getMessage());
                        }
                    }

                    setViewVisiable(emptyLayout, View.GONE);
                } else {
                    setViewVisiable(emptyLayout, View.VISIBLE);
                }
                setViewVisiable(loadingLayout, View.GONE);
            }
        }
        // Task结束
        else if (state == ABaseTaskState.finished) {
            //doNothing

        }
    }






    public void showMessage(CharSequence msg) {
        if (!TextUtils.isEmpty(msg) && getActivity() != null){
            ToastUtils.showToast(getActivity(),msg.toString());
        }

    }

    public void showMessage(int msgId) {
        if (getActivity() != null){
            showMessage(getString(msgId));
        }
    }

    public void showLoadingDialog(String msg){
        ViewUtils.createProgressDialog(getActivity(), msg).show();
    }

    public void showLoadingDialog(){
        showLoadingDialog(getString(R.string.loading_msg));
    }

    public void hideLoadingDialog(){
        ViewUtils.dismissProgressDialog();
    }


    /**
     * Fragment主要的刷新任务线程，定义任务加载流程，耦合Fragment各个状态下的视图刷新方法
     *
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     */
    protected abstract class ABaseTask<Params, Progress, Result> extends WorkTask<Params, Progress, Result> {

        public ABaseTask(String taskId) {
            super(taskId, ABaseFragment.this);
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();

            onTaskStateChanged(ABaseTaskState.prepare, null);
        }

        @Override
        protected void onSuccess(Result result) {
            super.onSuccess(result);

            // 默认加载数据成功，且ContentView有数据展示
            ABaseFragment.this.setContentEmpty(resultIsEmpty(result));

            onTaskStateChanged(ABaseTaskState.success, null);

            if (NLog.isDebug())
                NLog.d(TAG, "Result获取时间：%s", new SimpleDateFormat("HH:mm:ss").format(lastResultGetTime));

            if (result instanceof IResult) {
                IResult iResult = (IResult) result;

                // 数据是缓存数据
                if (iResult.isFromCache()) {
                    // 缓存过期刷新数据
                    if (iResult.isOutOfData()) {
                        runUIRunnable(new Runnable() {

                            @Override
                            public void run() {
                                NLog.d(TAG, "数据过期，开始刷新, " + toString());

                                requestDataOutofdate();
                            }

                        }, configRequestDelay());
                    }
                } else {
                    lastResultGetTime = System.currentTimeMillis();
                }
            } else {
                lastResultGetTime = System.currentTimeMillis();
            }
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);

            onTaskStateChanged(ABaseTaskState.falid, exception);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            onTaskStateChanged(ABaseTaskState.canceled, null);
        }

        @Override
        protected void onFinished() {
            super.onFinished();

            onTaskStateChanged(ABaseTaskState.finished, null);
        }

        /**
         * 返回数据是否空
         *
         * @param result
         * @return
         */
        public boolean resultIsEmpty(Result result) {
            return result == null ? true : false;
        }

    }


    public void runUIRunnable(Runnable runnable) {
        runUIRunnable(runnable, 0);
    }

    public void runUIRunnable(Runnable runnable, long delay) {
        if (delay > 0) {
            mHandler.removeCallbacks(runnable);
            mHandler.postDelayed(runnable, delay);
        }
        else {
            mHandler.post(runnable);
        }
    }

    public void requestDataOutofdate() {
        requestData();
    }




    public boolean isDestory() {
        return destory;
    }

    public boolean isActivityRunning() {
        return getActivity() != null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() != null && getActivity() instanceof BaseActivity){
             ((BaseActivity) getActivity()).addFragment(toString(), this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).removeFragment(this.toString());
    }


    @Override
     public final void  addTask(@SuppressWarnings("rawtypes") WorkTask task) {
        taskManager.addTask(task);
    }

    @Override
     public final void removeTask(String taskId, boolean cancelIfRunning) {
        taskManager.removeTask(taskId, cancelIfRunning);
    }

    @Override
    public final void removeAllTask(boolean cancelIfRunning) {
        taskManager.removeAllTask(cancelIfRunning);
    }

    @Override
     public final int getTaskCount(String taskId) {
        return taskManager.getTaskCount(taskId);
    }


    /**
     * 初步定义，当Task执行BizLogic方法时，第一次创建时拉取缓存，其他都只拉取网络
     *
     * @param task
     * @return
     */
    protected final ABizLogic.CacheMode getTaskCacheMode(WorkTask task) {
        if (task != null && !TextUtils.isEmpty(task.getTaskId()))
            return getTaskCount(task.getTaskId()) == 1 ? ABizLogic.CacheMode.auto : ABizLogic.CacheMode.disable;
        return ABizLogic.CacheMode.disable;
    }


    protected void setViewOnClick(View v) {
        if (v == null)
            return;

        v.setOnClickListener(innerOnClickListener);
    }

    View.OnClickListener innerOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onViewClicked(v);
        }

    };





    /**
     * 子类重写这个方法，初始化视图
     *
     * @param inflater
     * @param savedInstanceSate
     */
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {

    }

    private void initTitle(Bundle savedInstanceSate){
        String title = null ;
        if (getArguments() != null && savedInstanceSate == null){
            title = getArguments().getString("title");
        }else {
            if (savedInstanceSate != null){
                title = savedInstanceSate.getString("title");
            }
        }
        if (!TextUtils.isEmpty(title)){
            setToolbarTitle(title);
        }
    }



    /**
     * 指定Fragment的LayoutID
     *
     * @return
     */
     public abstract int inflateContentView();

    /**
     * 指定Activity的ContentViewID
     *
     * @return
     */
    public int inflateActivityContentView() {
        return -1;
    }

    /**
     * 设置Activity的Theme
     *
     * @return
     */
    public int setActivityTheme() {
        return -1;
    }

    public int configRequestDelay() {
        return 500;
    }

    public View getLoadingLayout() {
        return loadingLayout;
    }

    public View getLoadFailureLayout() {
        return loadFailureLayout;
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public View getEmptyLayout() {
        return emptyLayout;
    }



    protected void setToolbarTitle(String msg){
        BaseActivity baseActivity = (BaseActivity) getActivity();
        ActionBar supportActionBar = baseActivity.getSupportActionBar();
        NLog.d(TAG, "supportActionBar: %s",supportActionBar);
        if (supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(msg);
        }


    }

    protected void setToolbarTitle(int resId){
        setToolbarTitle(getString(resId));
    }

    @Override
    public boolean canSwipe() {
        return false;
    }


    /**
     * 是否显示图片接口实现
     */
    @Override
    public boolean canDisplay() {
        return true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        NLog.d(TAG, "setUserVisibleHint isVisibleToUser: %s",isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        NLog.d(TAG, "onHiddenChanged hidden: %s",hidden);
    }
}
