package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentCoroutineBennyhuoBinding
import com.apache.fastandroid.home.HomeModelFactory
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.HomeViewModelKt
import com.apache.fastandroid.util.InjectorUtil
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2022/3/20.
 */
class BennyHuoCoroutineDemoFragment:BaseVBFragment<FragmentCoroutineBennyhuoBinding>(FragmentCoroutineBennyhuoBinding::inflate) {

    private val viewModel:HomeViewModelKt by viewModels{InjectorUtil.getHomeModelFactory()}

    private var scope:CoroutineScope ?= null
    private var job:Job ?= null

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnStartPolling.setOnClickListener {
            doPoll()
        }

        viewBinding.btnStartPolling2.setOnClickListener {
            doPollByJob()
        }
        viewBinding.btnStopPolling.setOnClickListener {
            cancel()
        }

        viewModel.homeArticleLiveData.observe(this){
            println("homeArticleLiveData size:${it.datas?.size}")
        }

        viewModel.topArticleLiveData.observe(this){
            println("topArticleLiveData size:${it.size}")
        }
    }

    private fun cancel() {
        scope?.cancel()
        scope = null

        job?.cancel()
        job = null
    }


    private fun doPoll(){
        cancel()
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            while (isActive){
                try {
                    viewModel.loadHomeArticleCo(0)
                }catch (e:Exception){
                    Logger.d("doPoll coroutine exception:${e.message}")
                    e.printStackTrace()
                    if (e is CancellationException) throw e
                }

                delay(2000)
            }
        }
        this.scope = scope

    }

    private fun doPollByJob(){
        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                while (isActive){
                    try {
                        viewModel.loadTopArticle()
                    }catch (e:Exception){
                        Logger.d("doPollByJob exception:${e.message}")
                        e.printStackTrace()
                        if (e is CancellationException) throw e
                    }

                    delay(2000)
                }
            }
        }
    }
}