package com.apache.fastandroid

import android.app.Activity

import com.tesla.framework.ui.activity.BaseVmActivity
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
import com.apache.fastandroid.databinding.ActivityMainNewBinding
import com.apache.fastandroid.demo.kt.coroutine.BennyHuoCoroutineDemoFragment
import com.apache.fastandroid.jetpack.hit.HitDemoFragment
import com.tesla.framework.component.eventbus.FastBus
import com.tesla.framework.ui.activity.FragmentContainerActivity

class MainActivity : BaseVmActivity<ActivityMainNewBinding>(), View.OnClickListener {
    private val selecteId = -1
    override fun bindView(): ActivityMainNewBinding {
        return ActivityMainNewBinding.inflate(layoutInflater)
    }

    private var mNavController: NavController? = null
    @CostTime
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        setupDrawer(savedInstanceState)
        loadMenuData()
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
        mNavController = hostFragment!!.navController
        var configuration = AppBarConfiguration.Builder(mNavController!!.graph).build()
        configuration =
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
        FragmentContainerActivity.launch(this, HitDemoFragment::class.java)
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
        fun launch(from: Activity, userBean: UserBean?) {
            val intent = Intent(from, MainActivity::class.java)
            intent.putExtra("userBean", userBean)
            from.startActivity(intent)
        }
    }
}