package com.apache.fastandroid.demo.amitshekhar

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentAmitShakharBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/30.
 */
class AmitShekharDemoFragment:BaseBindingFragment<FragmentAmitShakharBinding>(FragmentAmitShakharBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnHttpEvolution.setOnClickListener {
            httpEvolution()
        }

    }

    /**
     * https://amitshekhar.me/blog/evolution-of-http
     * Http 1.0: 对同一服务器的每个请求都需要一个新的 TCP 连接
     * Http 1.1: 解决了 1.0 每次请求都需要一个新的 TCP 连接的问题，但是有一个新的问题:
     * HOL 阻塞问题。 HOL（队头）阻塞问题 - 新请求必须等待较早的请求完成。
     * HOL 阻塞在了 2 个层:
     * 应用层
     * 传输层(TCP)
     * Http 2.0:它利用请求复用的方式解决了HTTP 1.1应用层的HOL问题。但 TCP 上的 HOL 阻塞问题才是问题所在。
     * 问题：TCP 上的 HOL 阻塞问题。
     * Http 3.0:

     */
    private fun httpEvolution() {

    }
}