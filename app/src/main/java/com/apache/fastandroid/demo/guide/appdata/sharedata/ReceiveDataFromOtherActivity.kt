package com.apache.fastandroid.demo.guide.appdata.sharedata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import com.apache.fastandroid.databinding.ActivityReceiveDataFromOtherAppBinding
import com.tesla.framework.ui.activity.BaseBindingActivity

/**
 * Created by Jerry on 2024/2/27.
 * https://developer.android.com/training/sharing/receive?hl=zh-cn
 */
class ReceiveDataFromOtherActivity:BaseBindingActivity<ActivityReceiveDataFromOtherAppBinding>() {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        /**
         * 如需处理 Intent 传送的内容，请调用 getIntent() 以获取 Intent 对象。获得对象后，您可以检查其内容以确定下一步操作。
         * 如果此 activity 可以从系统的其他部分（例如启动器）启动，请在检查 intent 时考虑到这一点。
         *
         * 请格外小心检查传入的数据，因为您永远不知道其他应用可能会向您发送什么。例如，可能设置了错误的 MIME 类型，
         * 或者正在发送的图片可能非常大。此外，请记得在单独的线程而不是主（“界面”）线程中处理二进制数据
         */

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