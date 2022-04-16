package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentCoroutineExceptionBinding
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiServiceKt
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2021/10/28.
 * 协程异常处理
 * 参考：https://medium.com/androiddevelopers/exceptions-in-coroutines-ce8da1ec060c
 */
class CoroutineExceptionDemoFragment:BaseVBFragment<FragmentCoroutineExceptionBinding>(FragmentCoroutineExceptionBinding::inflate) {
    companion object{
        private const val TAG = "CoroutineCancelDemoFragment"
        private const val ARTICLE_ID = 21613L

    }

    private val viewModel:CoroutineVewModel by viewModels()

    private var job: Job? = null

    private val superScope = CoroutineScope(SupervisorJob())

    private val scope = CoroutineScope(Job())


    private val mainScope = MainScope()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnSupervisorJob.setOnClickListener {
            testSupervisorJob()
        }
        mBinding.btnSupervisorJob2.setOnClickListener {
            testSupervisorJob2()
        }
        mBinding.btnTryCatch.setOnClickListener {
            doHanleException()
        }
        mBinding.btnAwaitException.setOnClickListener {
            awaitException()
        }
        mBinding.btnCoroutineExceptionHandler.setOnClickListener {
            coroutineExceptionHandler()
        }

    }

    /**
     * when:在 coroutine 的 launcher函数中（非 async）
     * where:当在 CoroutineScope或者 根协程（CoroutineScope 或者 supervisorScope 的直接子类）   的 CoroutineContext中
     *
     */
    private fun coroutineExceptionHandler() {
        //launch 里面的异常，CoroutineExceptionHandler 会捕获到
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("catch :${throwable}")
        }
        scope.launch(handler) {
            launch {
                throw Exception("Failed Coroutine")
            }
        }

        //await 里面的异常，CoroutineExceptionHandler 不会捕获到
        scope.launch(handler) {
            launch {
                async {
                    val deferred = async {
                        ApiEngine.apiServiceKt.listReposKt("jerry")
                    }
                    deferred.await()
                }
            }
        }

    }

    private fun awaitException() {
        mainScope.launch {
            supervisorScope {
                val deferred = async {
                    ApiEngine.apiServiceKt.listReposKt("jerry")
                }
                try {
                    //调用 await 才可能抛出异常 Handle exception thrown in async
                    deferred.await()
                }catch (ex:Exception){
                    ToastUtils.showShort("await exception")
                    ex.printStackTrace()
                }
            }
        }
    }

    private fun testSupervisorJob2() {
        scope.launch {
            supervisorScope{
                launch {
                    delay(1000)
                    println("testSupervisorJob2 scope1 run")
                    throw CancellationException("testSupervisorJob2 job1 canceled")
                }
                launch {
                    delay(2000)
                    println("testSupervisorJob2 scope2 run")
                }
            }
        }
    }

    private fun testSupervisorJob(){
        superScope.launch {
            delay(1000)
            println("scope1 run")
            throw CancellationException("job1 canceled")
        }

        superScope.launch {
            delay(2000)
            println("scope12 run")
        }
    }

    private fun doHanleException() {
        mainScope.launch {
            viewModel.getArticleById(ARTICLE_ID)
        }

        mainScope.launch {
            viewModel.getArticleByIdWithRunCatching(ARTICLE_ID)
        }

    }

}

