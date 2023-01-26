package com.apache.fastandroid.demo.guide.appdata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import com.apache.fastandroid.databinding.ActivityReceiveDataBinding
import com.skydoves.bundler.intent
import com.tesla.framework.ui.activity.BaseBindingActivity

/**
 * Created by Jerry on 2023/1/26.
 */
class ReceiveDataDemoActivity: BaseBindingActivity<ActivityReceiveDataBinding>() {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        println("action:${intent?.action}, type:${intent.type}")

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                } else if (intent.type?.startsWith("image/") == true) {
                    handleSendImage(intent) // Handle single image being sent
                }
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {
                handleSendMultipleImages(intent) // Handle multiple images being sent
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }
    }


    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared

            mBinding.tvContent.text = "我是接收到的数据:$it"
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            // Update UI to reflect image being shared
        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            // Update UI to reflect multiple images being shared
        }
    }
}