package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import com.apache.fastandroid.R
import com.tesla.framework.ui.activity.BaseActivity

/**
 * Created by Jerry on 2021/10/19.
 * https://github.com/B3nedikt/ViewPump
 */
class ViewPumpDemoActivity:BaseActivity() {
    override fun inflateContentView(): Int {
        return R.layout.blacktech_view_pump
    }

    private var appCompatDelegate: AppCompatDelegate? = null


    override fun getDelegate(): AppCompatDelegate {
        if (appCompatDelegate == null) {
            appCompatDelegate = ViewPumpAppCompatDelegate(
                super.getDelegate(),
                this
            )
        }

        return appCompatDelegate!!
    }


}