package com.apache.fastandroid.jetpack.navigation

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.NavigationW960Binding
import com.google.android.material.navigation.NavigationView
import com.tesla.framework.ui.activity.BaseVmActivity

/**
 * Created by Jerry on 2022/3/11.
 */
class NavigationDrawerDemoActivity2:BaseVmActivity<NavigationW960Binding>(NavigationW960Binding::inflate) {

    private lateinit var appBarConfiguration : AppBarConfiguration

     override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

         val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)

        //抽屉或者大屏左边的导航栏
        setupNavigationMenu(navController)
        navController.addOnDestinationChangedListener { controller, destination, args ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }


            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }

    private fun setupNavigationMenu(navController: NavController) {

        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定时显示 抽屉图标还是 向上箭头?
         */
        setupActionBarWithNavController(navController, appBarConfig)

    }


    /**
     * 让右上角显示 setting 的图标
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        // TODO STEP 9.2 - Have Navigation UI Handle the item selection - make sure to delete
        //  the old return statement above
//        // Have the NavigationUI look for an action or destination matching the menu
//        // item id and navigate there if found.
//        // Otherwise, bubble up to the parent.
        val result =  item.onNavDestinationSelected(findNavController(R.id.nav_host)) || super.onOptionsItemSelected(item)
        return result

        // TODO END STEP 9.2
    }

    /**
     * onBackPressed和onSupportNavigateUp都可以使应用程序后退，很多时候的功能相同，但是在个别的场景下，还是有一定的区别
     * 在使用toolbar的情况下，onBackPressed可以使系统自带的后退按钮，onSupportNavigateUp可以使用toolbar的后退按钮后退
     */
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host).navigateUp(appBarConfiguration)
    }


}