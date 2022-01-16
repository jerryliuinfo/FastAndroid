package com.apache.fastandroid.demo.blacktech.viewpump

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.blacktech.ViewPumpDemoActivity
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class ViewPumpDemoFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.blacktech_view_pump
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        startActivity(Intent(context,ViewPumpDemoActivity::class.java))
    }

}