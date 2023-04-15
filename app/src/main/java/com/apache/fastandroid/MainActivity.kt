package com.apache.fastandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.apache.fastandroid.annotations.CostTime
import com.apache.fastandroid.databinding.ActivityMainNewBinding
import com.apache.fastandroid.demo.ToolsFragment
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.designmode.DesignModeDemoListFragment
import com.apache.fastandroid.demo.weaknetwork.connectionclass.ConnectionClassActivity
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.eventbus.FastBus
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVBActivity
import com.tesla.framework.ui.activity.FragmentContainerActivity
import timber.log.Timber

class MainActivity : BaseVBActivity<ActivityMainNewBinding>(ActivityMainNewBinding::inflate), View.OnClickListener {
    private val selecteId = -1

    private var mNavController: NavController? = null

    private val mHandler = Handler(Looper.getMainLooper())


    @CostTime
    override fun layoutInit(savedInstanceState: Bundle?) {
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

        Logger.d("MainActivity mHandler:${mHandler}")




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



       /* if (AppConfigUtil.isAppUpdated()){
            AppConfigUtil.appVersionCode = BuildConfig.VERSION_CODE.toLong()
        }*/


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
//        FragmentContainerActivity.launch(this, JuejinKtDemoListFragment::class.java,null)
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
//        FragmentContainerActivity.launch(this, BlackTechDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CrashReportDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, TempDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, MMKVKtxFragment::class.java,null)
//        FragmentContainerActivity.launch(this, CrashReportDemoFragment::class.java,null)

//        launchActivity<FluidResizeActivity>(this)
//        launchActivity<GithubBrowserMainActivity>(this)
//        LiveDataWrongUsageActivity.launch(this)

//        FragmentContainerActivity.launch(this, JetPackLifeCycleFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinHotQuestionFragment::class.java,null)
//        FragmentContainerActivity.launch(this, KotlinKnowledgeFragment2::class.java,null)
//        FragmentContainerActivity.launch(this, TempDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, DemoListFragment::class.java,null)
//        FragmentContainerActivity.launch(this, JniDemoListFragment::class.java,null)
//        FragmentContainerActivity.launch(this, JetPackLifeCycleDemoFragment::class.java,null)
//        FragmentContainerActivity.launch(this, ImmerseStatusBarDemoActivity::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, KnowledgeFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, SystemWidgetFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, FlowPracticeDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, FlowDemoListFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, ApiDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, CustomViewFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, NetworkDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, NetworkDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, DrakeetDemoListFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, KotlinTrapDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, LambdaDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, KotlinOfficalGramerFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, MMKVKtxFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, DatabindingViewModelFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, ResourcePoetDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, ListAdapterFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, WebViewFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, GuideDemoListFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, KotlinDemoListFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, CoroutineNetworkDemoFragment::class.java,null,addTitleBar = false)
//        FragmentContainerActivity.launch(this, ActivityPropertyDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, SearchPreferenceDemoListFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, AppDataDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, LiveDataLoaderFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, LocationDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, ProgressDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, CountDownDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, ElegantDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, ComponentDemoFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, DesignModeDemoListFragment::class.java,null,addTitleBar = true)
//        FragmentContainerActivity.launch(this, ToolsFragment::class.java,null,addTitleBar = true)

//        ListOptions().show(supportFragmentManager,"")

//        launchActivity<ConnectionClassActivity>(this)
//        launchActivity<ImmerseStatusBarDemoActivity>(this)
//         launchActivity<HitDemoActivity>(this)
//         launchActivity<BundleDemoActivity>(this)

//        launchActivity<TasksActivity>(this)

//        launchActivity<com.example.hellojnicallback.RewordDemoActivity>(this)


//        launchActivity<TasksActivity>(this)
//        launchActivity<MaterialDialogDemoActivity>(this)
//        launchActivity<ViewPumpDemoActivity>(this)

    //        launchActivity<CheeseActivity>(this)
//            launchActivity<ManifestDemoActivity>(this)

//        FragmentContainerActivity.launch(this, CustomLifecycleOwnerFragment::class.java,null,addTitleBar = false)
        onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - mExitTime > 2000){
                    mExitTime = System.currentTimeMillis()
                    showToast(R.string.main_exit_app)
                }else{
                    moveTaskToBack(true)
                }
            }

        })

    }



    private var mExitTime:Long = 0

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