package com.apache.fastandroid

import android.Manifest
import android.app.Activity

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import com.apache.fastandroid.annotations.CostTime
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.common.util.log.NLog
import com.blankj.utilcode.util.ToastUtils
import androidx.lifecycle.MutableLiveData
import com.android.example.github.GithubBrowserMainActivity
import com.apache.fastandroid.databinding.ActivityMainNewBinding
import com.apache.fastandroid.demo.fluidresize.FluidResizeActivity
import com.apache.fastandroid.demo.kt.KotlinOfficalGramerFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineRetrofitDemoFragment
import com.apache.fastandroid.jetpack.coroutine.CoroutineDemoListFragment
import com.apache.fastandroid.jetpack.flow.FlowDemoListFragment
import com.apache.fastandroid.jetpack.flow.serias.SerialNetworkFragment
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.permissionx.guolindev.PermissionX
import com.tesla.framework.component.eventbus.FastBus
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVmActivity
import com.tesla.framework.ui.activity.FragmentContainerActivity
import java.security.Permission

class MainActivity : BaseVmActivity<ActivityMainNewBinding>(ActivityMainNewBinding::inflate), View.OnClickListener {
    private val selecteId = -1

    private var mNavController: NavController? = null
    @CostTime
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        setSupportActionBar(mBinding.toolbar)
        setupDrawer(savedInstanceState)
        loadMenuData()
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
        mNavController = hostFragment!!.navController
        val configuration =
            AppBarConfiguration.Builder(R.id.home_dest, R.id.demo_dest).setOpenableLayout(
                mBinding!!.drawer
            ).build()
        setupActionBar(mNavController!!, configuration)
        setupNavigationMenu(mNavController!!)


//        FragmentContainerActivity.launch(this, KnowledgeFragment::class.java)
//        FragmentContainerActivity.launch(this, CoordinatorLayoutDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, JetPackDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, LyricDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, BennyHuoCoroutineDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, HitDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, RoomDemoFragment::class.java)
//        FragmentContainerActivity.launch(this, SunFlowerHomeViewPagerFragment::class.java,null,false)
//        FragmentContainerActivity.launch(this, CoroutineDemoListFragment::class.java,null,false)
//        FragmentContainerActivity.launch(this, CoroutineDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CoroutineCancelDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CoroutineCancelTimeoutDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CoroutineContextDispatcherDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinKnowledgeFragment::class.java,null)
//        FragmentContainerActivity.launch(this, FlowBasicUsageFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KnowledgeFragment::class.java,null)
//        FragmentContainerActivity.launch(this, BottomTabsFragment::class.java,null)
//        FragmentContainerActivity.launch(this, MvvmMailDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, MviDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, DatabindingViewModelFragment::class.java,null)
//        FragmentContainerActivity.launch(this, SystemViewFragment::class.java,null)
//        FragmentContainerActivity.launch(this, LyricFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinOfficalGramerFragment::class.java,null)
//        FragmentContainerActivity.launch(this, EncryptDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CustomViewGroupFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinOfficalGramerFragment::class.java,null)
//        FragmentContainerActivity.launch(this, SandWitchDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CollectionDemoFragment2::class.java,null)
//        FragmentContainerActivity.launch(this, KnowledgeFragment::class.java,null)
//        FragmentContainerActivity.launch(this, ProgressViewDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, OnceFragment::class.java,null)
//        FragmentContainerActivity.launch(this, FlowBasicUsageFragment::class.java,null)
//        FragmentContainerActivity.launch(this, SerialNetworkFragment::class.java,null)
//        FragmentContainerActivity.launch(this, FlowBasicUsageFragment::class.java,null)
//        FragmentContainerActivity.launch(this, FlowDemoListFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinOfficalGramerFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CoroutineDemoListFragment::class.java,null)

//        launchActivity<FluidResizeActivity>(this)
//        launchActivity<GithubBrowserMainActivity>(this)
//        LiveDataWrongUsageActivity.launch(this)

//        launchActivity<TasksActivity>(this)

    //        launchActivity<CheeseActivity>(this)



    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定是显示 抽屉图标还是 向上箭头?
         */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
    }

    private fun setupNavigationMenu(navController: NavController) {
        NavigationUI.setupWithNavController(mBinding!!.navigationView, navController)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val key = intent.getStringExtra("key")
        val user = intent.getSerializableExtra("user")
        if (user != null) {
            val userBean = user as UserBean
            NLog.d(TAG, "onNewIntent key: %s", key)
            NLog.d(TAG, "userBean: %s", userBean)
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
    }

    private fun loadMenuData() {}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selecteId", selecteId)
    }

    private fun setupDrawer(savedInstanceState: Bundle?) {
        val drawerToggle = object : ActionBarDrawerToggle(
            this, mBinding!!.drawer,
            mBinding!!.toolbar, R.string.draw_open, R.string.draw_close
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

    private var canFinish = false
    override fun onBackClick(): Boolean {
        if (!canFinish) {
            canFinish = true
            ToastUtils.showShort("再按一次退出")
            Handler().postDelayed({ canFinish = false }, 1500)
            return true
        }
        return super.onBackClick()
    }

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



}