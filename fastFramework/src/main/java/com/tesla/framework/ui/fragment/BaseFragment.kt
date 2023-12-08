package com.tesla.framework.ui.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.transition.MaterialFadeThrough
import com.kingja.loadsir.core.LoadService
import com.tesla.framework.R
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.component.vm.ShareViewModel
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.activity.BaseVBActivity
import com.tesla.framework.ui.fragment.view.BaseView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import com.tesla.framework.component.log.Timber
import com.tesla.framework.ui.fragment.base.IDynamicView

/**
 * Created by Jerry on 2022/3/10.
 */

open abstract class BaseFragment:Fragment(),BaseView,IDynamicView {
    private var isFirstLoad = true
    lateinit var mActivity: AppCompatActivity
    lateinit var mContext: Context
    lateinit var mShareViewModel: ShareViewModel

    lateinit var mRootView:View

    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    private var visible = false


    open abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View?

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mShareViewModel = getApplicationScopeViewModel(ShareViewModel::class.java)
        initViewModel()

        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflateView(inflater,container, savedInstanceState)?.also {
            mRootView = it
        }

        return rootView
    }

    open fun bindUI(rootView: View?) {}



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle()?.let {
            (requireActivity() as BaseActivity).setToolbarTitle(it)
        }
        bindUI(view)
        layoutInit(layoutInflater,savedInstanceState)
    }


    private  var mDynamicView: IDynamicView ?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
        mContext = requireActivity().baseContext

        mDynamicView = try {
            context as IDynamicView?
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            onLazyLoad()
        }
        onVisibilityChanged(true)

    }

    override fun onPause() {
        super.onPause()
        onVisibilityChanged(false)
    }


    open fun onVisibilityChanged(visible: Boolean) {
        Logger.d("${javaClass.simpleName} ==> onVisibilityChanged = $visible")
    }

    /**
     * Fragment 懒加载
     */
    protected open fun onLazyLoad() {}


    open fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    /**
     * 全局共享的ViewModel,类似于单例，但是跟单例不同的是，这个ViewModel仅限于在Activity或者Fragment当中使用
     * @param modelClass
     * @param <T>
     * @return
    </T> */
    open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(
                (mActivity.applicationContext as ViewModelStoreOwner),
                getApplicationFactory(mActivity)!!
            )
        }
        return mApplicationProvider!![modelClass]
    }

    private  fun getApplicationFactory(activity: Activity): ViewModelProvider.Factory? {
        checkActivity(this)
        val application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    private  fun checkActivity(fragment: Fragment) {
        val activity = fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }


    private  fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    protected open fun initViewModel() {}



    @CallSuper
    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    /**
     * 添加Disposable
     */
     fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private var loadService: LoadService<*>? = null

    /**
     * 注册 PlaceHolderView 默认显示PlaceHolderView
     * 数据加载成功需要调用 showSuccess(Constant.COMMON_KEY) 来显示原来的页面
     */
    override fun registerDefaultLoad(view: View, key: String) {
       /* val loadSir = LoadSir.Builder()
            .addCallback(PlaceHolderCallBack(placeHolderLayoutID))
            .addCallback(EmptyCallBack())
            .addCallback(ErrorCallBack())
            .setDefaultCallback(PlaceHolderCallBack::class.java)
            .build()
        loadService =  loadSir.register(view) {  reLoad() }*/
    }

    override fun showLoading(key: String) {
        println("showLoading key:$key")
    }

    override fun showSuccess(key: String) {
        println("showSuccess key:$key")
    }

    override fun showEmpty(key: String) {
        println("showEmpty key:$key")

    }

    override fun showError(msg: String, key: String) {
        println("showError key:$key")

    }

    /**
     * Action的home被点击了
     *
     * @return
     */
    open fun onHomeClick(): Boolean {
        return onBackClick()
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    open fun onBackClick(): Boolean {
        return false
    }

    fun toast(msg:String?){
        msg?.let {
            ToastUtils.showShort(msg)
        }
    }

    open fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {}


    fun toolbarTitle():String?{
        return null
    }

    open fun <T : View?> findViewById(@IdRes resId: Int): T {
        return mRootView.findViewById(resId)
    }

}