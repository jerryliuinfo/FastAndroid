package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.BlacktechViewPumpBinding
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.activity.BaseVmActivity
import kotlinx.android.synthetic.main.blacktech_view_pump.*
import java.util.*

/**
 * Created by Jerry on 2021/10/19.
 * https://github.com/B3nedikt/ViewPump
 */
class ViewPumpDemoActivity:BaseVmActivity<BlacktechViewPumpBinding>() {


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

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        button.setOnClickListener {

        }
    }

    override fun bindView(): BlacktechViewPumpBinding {
        return BlacktechViewPumpBinding.inflate(layoutInflater)
    }


}