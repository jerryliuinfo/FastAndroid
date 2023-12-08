package com.tesla.framework.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.tesla.framework.R
import com.tesla.framework.common.util.AndroidBugFixUtils
import com.tesla.framework.component.hook.HookResources
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.component.network.AutoRegisterNetListener
import com.tesla.framework.component.network.NetworkTypeEnum
import com.tesla.framework.performance.takt.Takt
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.fragment.base.IDynamicView
import com.tesla.framework.ui.widget.CustomToolbar.OnToolbarDoubleClickListener
import com.zwb.lib_base.utils.network.NetworkStateChangeListener
import java.lang.ref.WeakReference

/**
 * Created by Jerry on 2022/11/26.
 */
open abstract class BaseActivity: AppCompatActivity(), OnToolbarDoubleClickListener, IDynamicView {
    private  var mDelegate: BaseActivityDelegate ?= null

    // 当有Fragment Attach到这个Activity的时候，就会保存
    private var fragmentRefs: MutableMap<String, WeakReference<BaseFragment>>? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null



    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null
    private var toolbar: Toolbar? = null
    protected lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentRefs = HashMap()
        super.onCreate(savedInstanceState)
        activity = this


        initViewModel()
        initNetworkListener()

        inflateView().also {
            root = it
        }

        initView(root)
        layoutInit(savedInstanceState)

    }


    private fun getHelperDelegate():BaseActivityDelegate{
        if (mDelegate == null){
            mDelegate =
                BaseActivityDelegate(this, this)
        }
        return mDelegate!!
    }

    override fun onStart() {
        super.onStart()
        getHelperDelegate().onStart(this)
        Takt.play()
    }

    override fun onResume() {
        super.onResume()
        getHelperDelegate().onResume(this)
    }

    override fun onPause() {
        super.onPause()
        getHelperDelegate().onPause(this)
    }

    override fun onStop() {
        super.onStop()
        getHelperDelegate().onStop(this)
//        Takt.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        getHelperDelegate().onDestroy(this)
        activity = null
        AndroidBugFixUtils.fixSoftInputLeaks(this)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }
    open abstract fun inflateView():View





    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(object :NetworkStateChangeListener{
            override fun networkTypeChange(type: NetworkTypeEnum) {
                println("BaseVBActivity networkTypeChange type:$type")
                ToastUtils.showShort("网络状况: $type")
            }


            override fun networkConnectChange(isConnected: Boolean) {
                println("BaseVBActivity networkConnectChange isConnected:$isConnected")
                ToastUtils.showShort("网络状况: $isConnected")
            }

        }))

    }


    /**
     * 状态栏导航栏初始化
     */
    private fun initSystemBar() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    open fun setUpActionBar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)

        }
        supportActionBar?.apply {
            //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为Android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
            setDisplayShowHomeEnabled(true)
            //给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
            setDisplayHomeAsUpEnabled(true)

        }
    }

    open fun initView(rootView: View?) {
        window.statusBarColor = Color.TRANSPARENT // don't draw a status bar, the DrawerLayout and the MaterialDrawerLayout have their own

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.getString("statusTextSize", "medium")?.let {
            theme.applyStyle(textStyle(it), true)
        }

    }

    private  fun textStyle(name: String): Int {
        val style: Int
        when (name) {
            "smallest" -> style = R.style.TextSizeSmallest
            "small" -> style = R.style.TextSizeSmall
            "medium" -> style = R.style.TextSizeMedium
            "large" -> style = R.style.TextSizeLarge
            "largest" -> style = R.style.TextSizeLargest
            else -> style = R.style.TextSizeMedium
        }
        return style
    }


    open fun startActivityWithSlideInAnimation(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    @JvmOverloads
    fun showContent(target: Class<out BaseFragment>, bundle: Bundle? = null) {
        try {
            val fragment = target.newInstance()
            if (bundle != null) {
                fragment.arguments = bundle
            }
            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.add(android.R.id.content, fragment)
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 子类重写这个方法，初始化视图
     */
    open fun layoutInit(savedInstanceState: Bundle?) {}
    fun addFragment(tag: String, fragment: BaseFragment) {
        fragmentRefs!![tag] = WeakReference(fragment)
    }

    fun removeFragment(tag: String) {
        fragmentRefs!!.remove(tag)
    }

    /**
     * 暂时不实现这个方法，避免使用 drawalayout 时无法打开抽屉，满足了
     * item.itemId == android.R.id.home 的条件无法打开抽屉
     */
    /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (activityHelper != null) {
             val handle = activityHelper.onOptionsItemSelected(item)
             if (handle) {
                 return true
             }
         }
         if (item.itemId == android.R.id.home) {
             if (onHomeClick()) return true
         }
         return super.onOptionsItemSelected(item)
     }*/

    protected fun onHomeClick(): Boolean {
        if (mDelegate != null) {
            val handle = getHelperDelegate().onHomeClick()
            if (handle) return true
        }
        val keys: Set<String> = fragmentRefs!!.keys
        for (key in keys) {
            val fragmentRef = fragmentRefs!![key]!!
            val fragment = fragmentRef.get()
            if (fragment != null && fragment.onHomeClick()) return true
        }
        return onBackClick()
    }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (activityHelper != null) {
            val handle = activityHelper.onKeyDown(keyCode, event)
            if (handle) return true
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (onBackClick()) return true
        }
        return super.onKeyDown(keyCode, event)
    }*/

    open fun onBackClick(): Boolean {
        if (mDelegate != null) {
            val handle = getHelperDelegate().onBackClick()
            if (handle) return true
        }
        val keys: Set<String> = fragmentRefs!!.keys
        for (key in keys) {
            val fragmentRef = fragmentRefs!![key]!!
            val fragment = fragmentRef.get()
            if (fragment != null && fragment.onBackClick()) return true
        }
        finish()
        return true
    }

    override fun OnToolbarDoubleClick(): Boolean {
        val keys: Set<String> = fragmentRefs!!.keys
        for (key in keys) {
            val fragmentRef = fragmentRefs!![key]!!
            val fragment = fragmentRef.get()
            if (fragment != null && fragment is OnToolbarDoubleClickListener) {
                if ((fragment as OnToolbarDoubleClickListener).OnToolbarDoubleClick()) return true
            }
        }
        return false
    }

    fun setToolbarTitle(msg: String?) {
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = msg
        }
    }


    protected fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider =
                ViewModelProvider((this.applicationContext as ViewModelStoreOwner))
        }
        return mApplicationProvider!![modelClass]
    }

    /**
     * https://t.zsxq.com/uRBEaEE
     * @param overrideConfiguration
     */
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        Logger.d("applyOverrideConfiguration: %s ")
        if (overrideConfiguration != null) {
            //
            /* if (LocalMananger.locale != null){
                overrideConfiguration.setLocale(Loca);
            }*/
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    protected open fun initViewModel() {}

    companion object {
        const val TAG = "Activity-Base"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
//            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }




    fun showToast(msg: String?) {
        msg?.let {
            ToastUtils.showShort(msg)
        }
    }

    fun showToast(@StringRes msgId: Int) {
        showToast(getString(msgId))
    }

//    override fun getBaseContext(): Context {
//        val originContext =  super.getBaseContext()
//        return HookContext(originContext)
//    }



    private var mHookResources: HookResources?= null


    override fun getResources(): Resources {
        val originRes = super.getResources()
        if (mHookResources == null){
            mHookResources = HookResources(originRes)
        }
        val result = mHookResources!!
        if (result.configuration != originRes.configuration || result.displayMetrics != originRes.displayMetrics){
            result.updateConfiguration(originRes.configuration,originRes.displayMetrics)
        }
        return result
    }


    fun isActive():Boolean = getHelperDelegate().isActive


}