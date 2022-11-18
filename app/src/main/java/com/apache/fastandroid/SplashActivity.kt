package com.apache.fastandroid

import android.content.Intent
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.databinding.ActivitySplashBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by jerryliu on 2017/4/10.
 */
class SplashActivity : BaseVBActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        Logger.d("SplashActivity layoutInit")

        (application as FastApplication).appSetting.nightModeLive.observe(this) { nightMode ->
            nightMode?.let {
                AppCompatDelegate.setDefaultNightMode(it)
            }
        }

        mCountDownTimer.start()

        mBinding.tvCountDown.setOnClickListener {
            mCountDownTimer.cancel()
            toMain()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getParcelableExtra<Parcelable>("userBean") != null) {
            val userBean: UserBean = data.getParcelableExtra<Parcelable>("userBean") as UserBean
            Logger.d("userBean = %s", userBean)
        }
    }

    private var mCountDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val remainTime = "跳过|${millisUntilFinished / 1000} S"
            mBinding.tvCountDown.text = remainTime
            if (millisUntilFinished / 1000 == 1L) {
                toMain()
            }
            mBinding.tvCountDown.visibility = View.VISIBLE
        }

        override fun onFinish() {

        }

    }

    private fun toMain() {
        MainActivity.launch(this@SplashActivity)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}