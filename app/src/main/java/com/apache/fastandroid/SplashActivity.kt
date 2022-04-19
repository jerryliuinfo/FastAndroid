package com.apache.fastandroid

import android.content.Intent
import android.os.*
import android.view.View
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.databinding.ActivitySplashBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivity

/**
 * Created by jerryliu on 2017/4/10.
 */
class SplashActivity : BaseVmActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        Logger.d("SplashActivity layoutInit")
        time.start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getParcelableExtra<Parcelable>("userBean") != null) {
            val userBean: UserBean = data.getParcelableExtra<Parcelable>("userBean") as UserBean
            Logger.d("userBean = %s", userBean)
        }
    }

    private var time = object :CountDownTimer(5000,1000){
        override fun onTick(millisUntilFinished: Long) {
            val remainTime = "${millisUntilFinished / 1000} S"
            mBinding.tvCountDown.text = remainTime
            if(millisUntilFinished / 1000==1L ){
                MainActivity.launch(this@SplashActivity)
                finish()
            }
            mBinding.tvCountDown.visibility = View.VISIBLE
        }

        override fun onFinish() {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        time.cancel()
    }

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}