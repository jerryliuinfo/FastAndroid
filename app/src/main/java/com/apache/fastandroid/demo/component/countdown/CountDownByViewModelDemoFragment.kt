package com.apache.fastandroid.demo.component.countdown

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.R
import com.tesla.framework.databinding.FragmentCountdownBinding
import com.tesla.framework.ui.fragment.BaseBindingFragmentRef

/**
 * Created by Jerry on 2024/6/8.
 */
class CountDownByViewModelDemoFragment: BaseBindingFragmentRef<FragmentCountdownBinding>() {

    private lateinit var viewModel: CountdownViewModel
    private lateinit var countdownView: TextView

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        viewModel = ViewModelProvider(this).get(CountdownViewModel::class.java)
        countdownView = findViewById(R.id.countdown_view)

        viewModel.countdownState.observe(this, Observer { state ->
            countdownView.text = state.remainingTime.toString()
            if (!state.isRunning) {
                countdownView.setTextColor(Color.RED)
            } else {
                countdownView.setTextColor(Color.BLACK)
            }
        })

        findViewById<Button>(R.id.start_button).setOnClickListener {
            viewModel.startCountdown(60) // 60秒倒计时
        }
        findViewById<Button>(R.id.pause_button).setOnClickListener {
            viewModel.pauseCountdown()
        }
        findViewById<Button>(R.id.resume_button).setOnClickListener {
            viewModel.resumeCountdown()
        }
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            viewModel.restartCountdown(60) // 重启60秒倒计时
        }
        findViewById<Button>(R.id.stop_button).setOnClickListener {
            viewModel.stopCountdown()
        }
    }
}