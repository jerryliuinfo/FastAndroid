package com.apache.fastandroid.demo.navigation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentJetpackNavigationBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivity
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2022/1/22.
 */
class NavigationDemoActivity:BaseVmActivityNew<FragmentJetpackNavigationBinding>(FragmentJetpackNavigationBinding::inflate){

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout: DrawerLayout



    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        setupNavigationDrawer()

        //将 drawLayout中的id 和
        appBarConfiguration = AppBarConfiguration.Builder(R.id.fragment1,R.id.fragment2)
            .setOpenableLayout(drawerLayout)
            .build()

        val naviController:NavController = findNavController(R.id.nav_host_fragment)


        naviController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                Logger.d("destination:${destination}, arguments:${arguments.toString()}")
            }

        })
        //将 Navigation 和 NavController 关联起来
        mBinding.navView.setupWithNavController(naviController)
        //不设置这句代码的话会导致按toolbar的图标没法弹出 抽屉
        mBinding.toolbar.setupWithNavController(naviController,appBarConfiguration)
        //将 actionBar 和 NavController 关联起来 fragment切换时，appBar的title 会自动跟随fragment的label的变化而变化
        this.setupActionBarWithNavController(naviController, appBarConfiguration)

    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer(){
        drawerLayout = mBinding.drawerLayout.apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
    }


}