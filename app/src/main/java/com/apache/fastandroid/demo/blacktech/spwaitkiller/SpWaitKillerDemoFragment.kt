package com.apache.fastandroid.demo.blacktech.spwaitkiller

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentSpwaitKillerBinding
import com.apache.fastandroid.component.spkiller.SpWaitKiller
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseVBFragment
import org.lsposed.hiddenapibypass.HiddenApiBypass
import java.util.*
import java.util.concurrent.CountDownLatch


/**
 * Created by Jerry on 2022/4/15.
 * https://github.com/Knight-ZXW/SpWaitKiller
 */
class SpWaitKillerDemoFragment:BaseVBFragment<FragmentSpwaitKillerBinding>(FragmentSpwaitKillerBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("")
        }
        init()
    }

    private fun init() {
        mBinding.btnModeCase1
            .setOnClickListener {
                SpWaitKiller.builder(requireContext())
                    .build()
                    .work()
            }
        mBinding.mock
            .setOnClickListener {
                mockInsertHeavyWorkToQueuedWork(5)
                FragmentContainerActivity.launch(requireActivity(),SpWaitKillerDemoFragment2::class.java)
        }
    }




    private fun mockInsertHeavyWorkToQueuedWork(blockSeconds: Int) {
        try {
            val QueuedWorkClass = Class.forName("android.app.QueuedWork")
            val method = QueuedWorkClass.getDeclaredMethod("getHandler")
            method.isAccessible = true
            val handler = method.invoke(null) as Handler
            val looper = handler.looper
            @SuppressLint("SoonBlockedPrivateApi") val sWorkField =
                QueuedWorkClass.getDeclaredField("sWork")
            sWorkField.isAccessible = true
            val sWork: LinkedList<Runnable> = sWorkField[null] as LinkedList<Runnable>
            val sFinishersField = QueuedWorkClass.getDeclaredField("sFinishers")
            sFinishersField.isAccessible = true
            val finishers = sFinishersField[null] as MutableCollection<Runnable>
            val writtenToDiskLatch = CountDownLatch(1)
            val wait = Runnable {
                Log.e(
                    TAG,
                    "wait runnable run on thread " + Thread.currentThread().id + ", is MainThread ? " +
                            if (Thread.currentThread().id == Looper.getMainLooper().thread.id) " true" else " false"
                )
                try {
                    writtenToDiskLatch.await()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Log.e(TAG, "wait runnable end")
            }
            finishers.add(wait)
            sWork.add(object : Runnable {
                override fun run() {
                    try {
                        Log.d(
                            TAG, "run work " + Log.getStackTraceString(
                                Throwable()
                            )
                        )
                        Log.e(
                            TAG,
                            "run work " + this + " on Thread " + Thread.currentThread().id + " begin"
                        )
                        Thread.sleep((blockSeconds * 1000).toLong())
                        writtenToDiskLatch.countDown()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    Log.e(
                        TAG,
                        "run work " + this + " on Thread " + Thread.currentThread().id + " finish"
                    )
                }
            })
            //触发任务执行
            requireContext().getSharedPreferences("test", Context.MODE_PRIVATE)
                .edit().putString("k", "v")
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object{
        private const val TAG = "SensitiveWordDemoFragment"
    }



}