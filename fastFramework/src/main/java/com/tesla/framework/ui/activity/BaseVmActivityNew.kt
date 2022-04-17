package com.tesla.framework.ui.activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.tesla.framework.R
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.component.network.NetworkStateManager
import com.tesla.framework.ui.fragment.ABaseFragment
import com.tesla.framework.ui.widget.CustomToolbar.OnToolbarDoubleClickListener
import java.lang.ref.WeakReference

/**
 * Created by JerryLiu on 17/04/08.
 */
abstract class BaseVmActivityNew<V : ViewBinding>(var inflater: (inflater: LayoutInflater) -> V) : AppCompatActivity(),
    OnToolbarDoubleClickListener {
    protected lateinit var activityHelper: BaseActivityHelper
    protected lateinit var mBinding: V

    // 当有Fragment Attach到这个Activity的时候，就会保存
    private var fragmentRefs: MutableMap<String, WeakReference<ABaseFragment>>? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    /**
     * 判断当前Activity是否在前台。
     */
    protected var isActive = false

    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null
    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentRefs = HashMap()
        super.onCreate(savedInstanceState)
        activity = this
        mBinding = inflater(layoutInflater)
        setContentView(mBinding.root)
        activityHelper = BaseActivityHelper(this,this)
        initViewModel()
//        setUpActionBar()
        lifecycle.addObserver(NetworkStateManager.getInstance())
        initView(mBinding.root)
        layoutInit(savedInstanceState)


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

    open fun initView(rootView: View?) {}
    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    @JvmOverloads
    fun showContent(target: Class<out ABaseFragment>, bundle: Bundle? = null) {
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
    fun addFragment(tag: String, fragment: ABaseFragment) {
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
        if (activityHelper != null) {
            val handle = activityHelper.onHomeClick()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (activityHelper != null) {
            val handle = activityHelper.onKeyDown(keyCode, event)
            if (handle) return true
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (onBackClick()) return true
        }
        return super.onKeyDown(keyCode, event)
    }

    open fun onBackClick(): Boolean {
        if (activityHelper != null) {
            val handle = activityHelper.onBackClick()
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

    override fun onDestroy() {
        super.onDestroy()
        activity = null
    }

    protected fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun <T : ViewModel?> getApplicationScopeViewModel(modelClass: Class<T>): T {
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
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}