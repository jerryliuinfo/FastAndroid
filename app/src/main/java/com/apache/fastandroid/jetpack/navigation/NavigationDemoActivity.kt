package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityNavigationDemoBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2022/3/10.
 */
class NavigationDemoActivity:BaseVmActivityNew<ActivityNavigationDemoBinding>(ActivityNavigationDemoBinding::inflate) {
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        setSupportActionBar(mBinding.toolbar)

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        val navController = hostFragment.navController
        navController.addOnDestinationChangedListener{ controller,destination,args ->
            val des:String = try{
                resources.getResourceName(destination.id)
            }finally {
                Integer.toString(destination.id)
            }
            ToastUtils.showLong("Navigated to $des")
            Logger.d("Navigated to $des")

        }
        setupBottomNavigationView(navController)
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

}