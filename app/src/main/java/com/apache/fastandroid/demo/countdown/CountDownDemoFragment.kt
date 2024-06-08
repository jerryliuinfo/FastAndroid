package com.apache.fastandroid.demo.countdown

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.countdown.coroutine.CoroutineCountdownStrategy
import com.tesla.framework.databinding.FragmentCountdownBinding
import com.tesla.framework.ui.fragment.BaseBindingFragmentRef

/**
 * Created by Jerry on 2024/6/8.
 */
class CountDownDemoFragment:BaseBindingFragmentRef<FragmentCountdownBinding>() {



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val component = CountdownComponent(CoroutineCountdownStrategy(){
            mBinding.countdownView.text = it.first.toString()
            if (!it.second) {
                mBinding.countdownView.setTextColor(Color.RED)
            } else {
                mBinding.countdownView.setTextColor(Color.BLACK)
            }

        })

        findViewById<Button>(R.id.start_button).setOnClickListener {
            component.start(10) // 60秒倒计时
        }
        findViewById<Button>(R.id.pause_button).setOnClickListener {
            component.pause()
        }
        findViewById<Button>(R.id.resume_button).setOnClickListener {
            component.resume()
        }
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            component.reset(10) // 重启60秒倒计时
        }
        findViewById<Button>(R.id.stop_button).setOnClickListener {
            component.stop()
        }

    }
}