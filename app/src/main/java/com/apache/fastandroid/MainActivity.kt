package com.apache.fastandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Trace
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.android.androidtech.monitor.time.TimeMonitorConfig
import com.android.androidtech.monitor.time.TimeMonitorManager
import com.apache.fastandroid.annotations.CostTime
import com.apache.fastandroid.databinding.ActivityMainNewBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.sunflower.fragement.SunFlowerHomeViewPagerFragment
import com.tesla.framework.common.util.LaunchTimer
import com.tesla.framework.component.eventbus.FastBus
import com.tesla.framework.component.log.Timber
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.ui.activity.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainNewBinding>(), View.OnClickListener {
    private val selecteId = -1

    private var mNavController: NavController? = null

    private val mHandler = Handler(Looper.getMainLooper())

    private val recycledViewPool = RecyclerView.RecycledViewPool().apply {
        setMaxRecycledViews(R.id.view_type_recycleView_pool, 25)
    }


    @CostTime
    override fun layoutInit(savedInstanceState: Bundle?) {
        Trace.beginSection("MainActivity layoutInit")
        TimeMonitorManager.getInstance()
            .getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START)
            .recodingTimeTag("AppStartActivity_create")

        super.layoutInit(savedInstanceState)

        // If the app's main task was not created using the default launch intent (e.g. from a notification, a widget,
        // or a shortcut), using the app icon to "launch" the app will create a new MessageList instance instead of only
        // bringing the app's task to the foreground. We catch this situation here and simply finish the activity. This
        // will bring the task to the foreground, showing the last active screen.
        if (intent.action == Intent.ACTION_MAIN && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && !isTaskRoot) {
            Timber.v("Not displaying MessageList. Only bringing the app task to the foreground.")
            finish()
            return
        }

        Logger.d("MainActivity mHandler:${mHandler},toolbar:${mBinding.toolbar}, toolbar2:${mBinding.toolbar}")
//        Logger.d("MainActivity binding:${binding},mBinding:${mBinding}")


        setSupportActionBar(mBinding.toolbar)
        setupDrawer(savedInstanceState)
        loadMenuData()
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
        mNavController = hostFragment?.navController
        val configuration =
            AppBarConfiguration.Builder(R.id.home_dest, R.id.demo_dest).setOpenableLayout(
                mBinding.drawer
            ).build()
        setupActionBar(mNavController!!, configuration)
        setupNavigationMenu(mNavController!!)


        //        launchActivity<PagingPosterDemoActivity>(this)
        // launchFragment<JetPackDemoFragment>()
        launchFragment<SunFlowerHomeViewPagerFragment>(addTitleBar = false)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                when {
                    mBinding.drawer.isOpen -> {
                        mBinding.drawer.close()
                    }

                    else -> {
                        if (System.currentTimeMillis() - mExitTime > 2000) {
                            mExitTime = System.currentTimeMillis()
                            showToast(R.string.main_exit_app)
                        } else {
                            moveTaskToBack(true)
                        }
                    }
                }
            }

        })
        Trace.endSection()

        TimeMonitorManager.getInstance()
            .getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START)
            .recodingTimeTag("AppStartActivity_createOver")


    }

    override fun onStart() {
        super.onStart()
        TimeMonitorManager.getInstance()
            .getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START)
            .end("AppStartActivity_start", false)

    }


    private var mExitTime: Long = 0

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration,
    ) {
        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定是显示 抽屉图标还是 向上箭头?
         */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
    }

    private fun setupNavigationMenu(navController: NavController) {
        NavigationUI.setupWithNavController(mBinding.navigationView, navController)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val key = intent.getStringExtra("key")
        val user = intent.getSerializableExtra("user")
        if (user != null) {
            val userBean = user as UserBean
        }
    }


    private fun loadMenuData() {}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selecteId", selecteId)
    }

    private fun setupDrawer(savedInstanceState: Bundle?) {
        val drawerToggle = object : ActionBarDrawerToggle(
            this, mBinding.drawer,
            mBinding.toolbar, R.string.draw_open, R.string.draw_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        mBinding.drawer.addDrawerListener(drawerToggle)
    }

    /*  private var canFinish = false
      override fun onBackClick(): Boolean {
          if (!canFinish) {
              canFinish = true
              ToastUtils.showShort("再按一次退出")
              Handler().postDelayed({ canFinish = false }, 1500)
              return true
          }
          return super.onBackClick()
      }*/

    private val result = MutableLiveData<Boolean>()
    override fun onClick(v: View) {
        result.value = !result.value!!
    }

    public override fun onDestroy() {
        super.onDestroy()
        FastBus.getInstance().unregiste(this)
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        fun launch(from: Activity, userBean: UserBean? = null) {
            val intent = Intent(from, MainActivity::class.java)
            intent.putExtra("userBean", userBean)
            from.startActivity(intent)
        }
    }


    /**
     * onWindowFocusChanged Activity 第一帧， 并不是用户看到的真实数据展示时间，对于 Feed 列表来说可以取第一条数据
     * @param hasFocus Boolean
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        LaunchTimer.endRecord("onWindowFocusChanged")
    }


}