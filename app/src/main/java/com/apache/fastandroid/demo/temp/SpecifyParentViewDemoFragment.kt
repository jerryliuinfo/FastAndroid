package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/26.
 */
class SpecifyParentViewDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.temp_specify_parent_view
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // items.add("onDrawerSlide($slideOffset)")
                // adapter.notifyDataSetChanged()
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
    }
}