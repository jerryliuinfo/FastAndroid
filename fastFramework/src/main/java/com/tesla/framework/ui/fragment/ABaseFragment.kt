package com.tesla.framework.ui.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kingja.loadsir.core.LoadService
import com.tesla.framework.component.vm.ShareViewModel
import com.tesla.framework.ui.fragment.view.BaseView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Created by Jerry on 2022/3/10.
 */
open class ABaseFragment:Fragment(),BaseView {
    private var isFirstLoad = true
    lateinit var mActivity: AppCompatActivity
    lateinit var mContext: Context
    lateinit var mShareViewModel: ShareViewModel

    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mShareViewModel = getApplicationScopeViewModel(ShareViewModel::class.java)
        initViewModel()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
        mContext = requireActivity().baseContext
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            onLazyLoad()
        }
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
    open fun <T : ViewModel?> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(
                (mActivity.getApplicationContext() as ViewModelStoreOwner),
                getApplicationFactory(mActivity)!!
            )
        }
        return mApplicationProvider!!.get(modelClass)
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
    }

    override fun showSuccess(key: String) {
    }

    override fun showEmpty(key: String) {
    }

    override fun showError(msg: String, key: String) {
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
}