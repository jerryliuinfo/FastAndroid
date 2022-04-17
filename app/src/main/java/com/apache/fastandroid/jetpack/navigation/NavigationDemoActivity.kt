package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityNavigationDemoBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivity

/**
 * Created by Jerry on 2022/3/10.
 */
class NavigationDemoActivity:BaseVmActivity<ActivityNavigationDemoBinding>(ActivityNavigationDemoBinding::inflate) {

    private lateinit var appBarConfiguration : AppBarConfiguration

    private lateinit var navController: NavController

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        setSupportActionBar(mBinding.toolbar)

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        navController = hostFragment.navController

        /*Fragment.findNavController()
        View.findNavController()
        Activity.findNavController(viewId: Int)*/



        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupBottomNavigationView(navController)

        setupActionBar(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener{ controller,destination,args ->
            val des:String = try{
                resources.getResourceName(destination.id)
            }finally {
                Integer.toString(destination.id)
            }
            ToastUtils.showLong("Navigated to $des")
            Logger.d("Navigated to $des")

        }
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {

        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定是显示 抽屉图标还是 向上箭头?
         */
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupBottomNavigationView(controller: NavController) {
        mBinding.bottomNavView.setupWithNavController(controller)
    }

    /**
     * 让右上角显示 setting 的图标
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.overflow_menu,menu)
        return true
    }


    /**
     * 响应 Actionbar 菜单点击
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host))
                || super.onOptionsItemSelected(item)

    }

    /**
     * onBackPressed和onSupportNavigateUp都可以使应用程序后退，很多时候的功能相同，但是在个别的场景下，还是有一定的区别
     * 在使用toolbar的情况下，onBackPressed可以使系统自带的后退按钮，onSupportNavigateUp可以使用toolbar的后退按钮后退
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}