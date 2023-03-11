package com.apache.fastandroid.demo.hitpit

import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentHitPitBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/12/12.
 * CustomImageView 的 onDraw 方法中调用 setImageResource 会触发 scheduleTravasel 导致主线程消息队列一直不为空，导致
 * queueIdle 一直不被执行
 * 参考:https://juejin.cn/post/6936440588635996173?share_token=e3cb2750-a5d9-498b-b345-1539fe421665
 *
 */
class MessageQueueNotIdleFragment:BaseBindingFragment<FragmentHitPitBinding>(FragmentHitPitBinding::inflate) {
    companion object{
        private val TAG = "HitPitDemoListFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        Looper.getMainLooper().queue.addIdleHandler(object :MessageQueue.IdleHandler{
            override fun queueIdle(): Boolean {
                return false
            }

        })
    }


}