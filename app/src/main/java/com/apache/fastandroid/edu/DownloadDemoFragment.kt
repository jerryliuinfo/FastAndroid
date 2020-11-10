package com.apache.fastandroid.edu

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.ui.widget.ToastUtils
import kotlinx.android.synthetic.main.fragment_download.*

/**
 * Created by Jerry on 2020/11/9.
 */
class DownloadDemoFragment:BaseFragment() {
    companion object{
        private val mUrl = "http://apk.aotclouds.net/image/b2d38bc7-f215-4dc8-8b61-7be54dd68192.png"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_download
    }

    private lateinit var statusLayout:DownloadStatusView

    private var downloadProgress:Int = 0

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            downloadProgress += 1
            if (downloadProgress >= 100){
                statusLayout.setDownloadFinishedStatus()
            }else{
                statusLayout.setDownloadingStatus(downloadProgress)
                sendEmptyMessageDelayed(1,100)
            }
        }
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        statusLayout = findViewById(R.id.status_layout) as DownloadStatusView
        statusLayout.setWaitingStatus()
        statusLayout.setDownloadStatusListener(object :DownloadStatusView.IDownloadStatusListener{
            override fun onPauseDownload() {
               ToastUtils.showSingleToast("暂停下载")
            }

            override fun animationFinish() {
                ToastUtils.showSingleToast("下载完成动画完成")
            }

        })

        btn_waiting_download.setOnClickListener {
            statusLayout.setWaitingStatus()
        }
        btn_downloading.setOnClickListener {
            downloadProgress = 0
            handler.sendEmptyMessage(1)

        }
        btn_downloaded.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            statusLayout.setDownloadFinishedStatus()
        }


    }

}