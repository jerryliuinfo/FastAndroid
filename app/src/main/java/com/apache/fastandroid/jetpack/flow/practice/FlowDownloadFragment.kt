package com.apache.fastandroid.jetpack.flow.practice

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FlowFragmentDownloadBinding
import com.example.flowpractice.download.DownLoadManager
import com.example.flowpractice.download.DownloadStatus
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.File

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowDownloadFragment:BaseBindingFragment<FlowFragmentDownloadBinding>(FlowFragmentDownloadBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val URL = "https://tse1-mm.cn.bing.net/th/id/R-C.8adb5361a5be9608307725939c365b9f?rik=BSbwLtF73fVzjQ&riu=http%3a%2f%2fwww.dnzhuti.com%2fuploads%2fallimg%2f170323%2f95-1F323144448.jpg&ehk=tBNKAIxpdhwEe%2bM54QdzfbpHFo65HWl9Z8HKmCnSW60%3d&risl=&pid=ImgRaw&r=0"
        lifecycleScope.launchWhenCreated {
            context?.apply {
                val file = File(externalCacheDir,"love.jpg")

                val mFlow = DownLoadManager.download(URL,file)
//                mFlow.cancellable()
                mFlow.collect { status ->
                    when(status){
                        is DownloadStatus.Progress ->{
                            mBinding.progressBar.progress = status.value
                            mBinding.text.text="${status.value}%"
                        }
                        is DownloadStatus.Error->{
                            Toast.makeText(activity,"下载错误", Toast.LENGTH_LONG).show()
                        }
                        is DownloadStatus.Done->{
                            mBinding.progressBar.progress = 100
                            mBinding.text.text = "100%"
                            Toast.makeText(activity,"下载完成", Toast.LENGTH_LONG).show()

                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            bitmap?.let {
                                mBinding.image.setImageBitmap(it)
                            }
                        }
                        else -> "error"
                    }
                }
            }

        }
    }
}