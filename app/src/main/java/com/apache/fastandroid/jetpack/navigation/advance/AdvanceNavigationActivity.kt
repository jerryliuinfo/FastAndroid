package com.apache.fastandroid.jetpack.navigation.advance

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityNavigationAdvanceBinding
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2022/3/12.
 */
class AdvanceNavigationActivity:BaseVmActivityNew<ActivityNavigationAdvanceBinding>(ActivityNavigationAdvanceBinding::inflate) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        setSupportActionBar(mBinding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.titleScreen,R.id.leaderboard))

        mBinding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}