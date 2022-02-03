package com.apache.fastandroid.demo.performance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentCockroachBinding
import com.apache.fastandroid.demo.performance.cockroach.LifecycleExceptionActivity
import com.apache.fastandroid.demo.performance.cockroach.UnknowAct
import com.tesla.framework.ui.fragment.BaseVMFragment
import java.lang.RuntimeException

/**
 * Created by Jerry on 2022/2/3.
 */
class CockroachDemoFragment:BaseVMFragment<FragmentCockroachBinding>(FragmentCockroachBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        findViewById<View>(R.id.click).setOnClickListener {
            throw RuntimeException(
                "点击异常"
            )
        }
        findViewById<View>(R.id.thread).setOnClickListener {
            object : Thread() {
                override fun run() {
                    super.run()
                    throw RuntimeException("子线程异常")
                }
            }.start()
        }
        findViewById<View>(R.id.handler).setOnClickListener {
            Handler().post { throw RuntimeException("handler异常") }
        }

        /*findViewById<View>(R.id.act).setOnClickListener {
            startActivity(
                Intent(
                    this@MainAct,
                    SecondAct::class.java
                )
            )
        }*/

        findViewById<View>(R.id.noact).setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    UnknowAct::class.java
                )
            )
        }
        ////////黑屏测试//////////
        ////////黑屏测试//////////
        findViewById<View>(R.id.newActOnCreate).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onCreate")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActOnStart).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onStart")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActOnReStart).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onRestart")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActOnResume).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onResume")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActOnPause).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onPause")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActOnStop).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onStop")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActonDestroy).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "onDestroy")
            startActivity(intent)
        }
        findViewById<View>(R.id.newActFinish).setOnClickListener {
            val intent: Intent = Intent(
                requireActivity(),
                LifecycleExceptionActivity::class.java
            )
            intent.putExtra(LifecycleExceptionActivity.METHOD, "finish")
            startActivity(intent)
        }
    }
}