package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.TempSpecifyParentViewBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/10/26.
 */
class SpecifyParentViewDemoFragment:BaseVBFragment<TempSpecifyParentViewBinding>(TempSpecifyParentViewBinding::inflate) {

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