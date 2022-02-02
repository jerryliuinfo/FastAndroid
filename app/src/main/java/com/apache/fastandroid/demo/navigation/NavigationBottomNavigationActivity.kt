package com.apache.fastandroid.demo.navigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityNavigationBottomNavigationBinding
import com.tesla.framework.ui.activity.BaseVmActivity
import com.tesla.framework.ui.activity.BaseVmActivityNew

class NavigationBottomNavigationActivity : BaseVmActivityNew<ActivityNavigationBottomNavigationBinding>(ActivityNavigationBottomNavigationBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        val navView: BottomNavigationView = mBinding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigation_bottom_navigation)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

}