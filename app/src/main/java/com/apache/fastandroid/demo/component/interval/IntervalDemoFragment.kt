package com.apache.fastandroid.demo.component.interval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentIntervalBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2023/5/2.
 */
class IntervalDemoFragment:BaseBindingFragment<FragmentIntervalBinding>(FragmentIntervalBinding::inflate) {

    private lateinit var interval: Interval // 轮询器

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        interval = Interval(0, 1, TimeUnit.SECONDS, 10).life(this) // 自定义计数器个数的轮询器, 当[start]]比[end]值大, 且end不等于-1时, 即为倒计时
        // interval = Interval(1, TimeUnit.SECONDS) // 每秒回调一次, 不会自动结束
        interval.subscribe {
            println("onTick it:$it")
            mBinding.tvFragment.text = it.toString()
        }.finish {
            println("onTick onFinish")
            mBinding.tvFragment.text = "计时完成"
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_interval, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start -> interval.start()
            R.id.pause -> interval.pause()
            R.id.resume -> interval.resume()
            R.id.reset -> interval.reset()
            R.id.switch_interval -> interval.switch()
            R.id.stop -> interval.stop()
            R.id.cancel -> interval.cancel()
        }
        return super.onOptionsItemSelected(item)

    }
}