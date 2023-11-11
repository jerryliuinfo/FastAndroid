package com.apache.fastandroid

import android.content.Intent
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.databinding.ActivitySplashBinding
import com.tesla.framework.component.countdown.ICountDownAction
import com.tesla.framework.component.countdown.ICountDownListener
import com.tesla.framework.component.countdown.LifeCycleCountDownByTimer
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by jerryliu on 2017/4/10.
 */
class SplashActivity : BaseVBActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    private var mTimer: ICountDownAction? = null

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        Logger.d("SplashActivity layoutInit")

        Initiator.appSetting.nightModeLive.observe(this) { nightMode ->
            nightMode?.let {
                AppCompatDelegate.setDefaultNightMode(it)
            }
        }


        mTimer = LifeCycleCountDownByTimer(
            lifecycleOwner = this,
            listener = object : ICountDownListener {
                override fun onTick(count: Long) {
                    val remainTime = "跳过|$count S"
                    runOnUiThread {
                        mBinding.tvCountDown.apply {
                            text = remainTime
                            visibility = View.VISIBLE
                        }
                    }


                }

                override fun onFinish() {
                    toMain()
                }

            })

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

    private var mCountDownTimer = object : CountDownTimer(4000, 1000) {
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
        runOnUiThread {
            MainActivity.launch(this@SplashActivity)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}