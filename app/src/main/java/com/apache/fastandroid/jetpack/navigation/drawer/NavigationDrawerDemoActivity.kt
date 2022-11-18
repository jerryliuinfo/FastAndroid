package com.apache.fastandroid.jetpack.navigation.drawer

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.NavigationDrawerActivityBinding
import com.google.android.material.navigation.NavigationView
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/3/11.
 */
class NavigationDrawerDemoActivity:BaseVBActivity<NavigationDrawerActivityBinding>(NavigationDrawerActivityBinding::inflate) {

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

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.deeplink_dest),
            drawerLayout)
        // TODO END STEP 9.5

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
        // TODO STEP 9.4 - Use NavigationUI to set up a Navigation View
//        // In split screen mode, you can drag this view out from the left
//        // This does NOT modify the actionbar
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
        // TODO END STEP 9.4
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定时显示 抽屉图标还是 向上箭头?
         */
        setupActionBarWithNavController(navController, appBarConfig)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // The NavigationView already has these same navigation items, so we only add
        // navigation items to the menu here if there isn't a NavigationView
        if (navigationView == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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