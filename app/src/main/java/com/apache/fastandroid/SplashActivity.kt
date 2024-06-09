package com.apache.fastandroid

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatDelegate
import com.apache.fastandroid.databinding.ActivitySplashBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.countdown.CountdownComponent
import com.apache.fastandroid.demo.countdown.coroutine.CoroutineCountdownStrategy
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.show
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by jerryliu on 2017/4/10.
 */
class SplashActivity : BaseVBActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    // private var mTimer: ICountDownAction? = null

    private lateinit var mComponent: CountdownComponent

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        Logger.d("SplashActivity layoutInit")

        Initiator.appSetting.nightModeLive.observe(this) { nightMode ->
            nightMode?.let {
                AppCompatDelegate.setDefaultNightMode(it)
            }
        }


        // mTimer = LifeCycleCountDownByTimer(
        //     lifecycleOwner = this,
        //     listener = object : ICountDownListener {
        //         override fun onTick(count: Long) {
        //             val remainTime = "跳过|$count S"
        //             runOnUiThread {
        //                 mBinding.tvCountDown.apply {
        //                     text = remainTime
        //                     visibility = View.VISIBLE
        //                 }
        //             }
        //
        //
        //         }
        //
        //         override fun onFinish() {
        //             toMain()
        //         }
        //
        //     })
        //
        // mCountDownTimer.start()

        mBinding.tvCountDown.setOnClickListener {
            // mCountDownTimer.cancel()
            // toMain()
        }

        mComponent = CountdownComponent(CoroutineCountdownStrategy(){
            mBinding.tvCountDown.show()
            mBinding.tvCountDown.text = "跳过|${it.first.toString()} S"
            if (it.first.toInt() == 0){
                toMain()
            }

        }).start(3)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getParcelableExtra<Parcelable>("userBean") != null) {
            val userBean: UserBean = data.getParcelableExtra<Parcelable>("userBean") as UserBean
            Logger.d("userBean = %s", userBean)
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
        // mComponent.stop()
    }

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}