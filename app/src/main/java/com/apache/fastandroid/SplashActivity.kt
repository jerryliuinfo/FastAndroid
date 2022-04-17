package com.apache.fastandroid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.databinding.ActivitySplashBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by jerryliu on 2017/4/10.
 */
class SplashActivity : BaseVmActivityNew<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        Logger.d("SplashActivity layoutInit")
        Handler().postDelayed({ toMain() }, 2000)
    }

    private fun toMain() {
        val userBean = UserBean("Tom", 10)
        MainActivity.launch(this@SplashActivity, userBean)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getParcelableExtra<Parcelable>("userBean") != null) {
            val userBean: UserBean = data.getParcelableExtra<Parcelable>("userBean") as UserBean
            Logger.d("userBean = %s", userBean)
        }
    }



    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}