package com.tesla.framework.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.component.imageloader.BitmapOwner;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.task.ITaskManager;
import com.tesla.framework.network.task.TaskManager;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.InjectUtility;
import com.tesla.framework.ui.activity.BaseActivity;
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


    View rootView;// 根视图

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

            return getRootView();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startTime3 = System.currentTimeMillis();
        initTitle(savedInstanceState);
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

    }

    public void setContentView(ViewGroup view) {
        this.rootView = view;
    }

    /**
     * 根视图
     *
     * @return
     */
    public View getRootView() {
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
        InjectUtility.initInjectedView(getActivity(), this, getRootView());

    }

    public  <T extends View>T findViewById(int viewId) {
        if (getRootView() == null) {
            return null;
        }

        return getRootView().findViewById(viewId);
    }


    protected void setViewVisiable(View v, int visibility) {
        if (v != null && v.getVisibility() != visibility) {
            v.setVisibility(visibility);
        }
    }

    public void showMessage(CharSequence msg) {

        ToastUtils.showShort(msg.toString());
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



    public int configRequestDelay() {
        return 500;
    }




    protected void setToolbarTitle(String msg){
        BaseActivity baseActivity = (BaseActivity) getActivity();
        ActionBar supportActionBar = baseActivity.getSupportActionBar();
        FastLog.d(TAG, "supportActionBar: %s",supportActionBar);
        if (supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(msg);
        }
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



}
