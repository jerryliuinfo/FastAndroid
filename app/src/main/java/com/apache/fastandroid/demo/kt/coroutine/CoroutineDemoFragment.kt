package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.article.ArticleNetwork
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.model.result.BaseResponse
import com.apache.fastandroid.network.model.result.EmptyResponse
import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.awaitNextLayout
import com.tesla.framework.ui.fragment.BaseBindingFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineDemoFragment :
    BaseBindingFragment<FragmentKotlinCouritineBinding>(FragmentKotlinCouritineBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineDemoFragment"
    }

    private val viewModel:CoroutineVewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        initRetrofit()

        mBinding.btnTraditionalSwitchThread.setOnClickListener {
            traditionalSwitchThread()
        }
        mBinding.btnSuspendCoroutine.setOnClickListener {
            suspendCoroutineUsage()
        }

        mBinding.btnJobDispatcher.setOnClickListener {
            jobDispatcher()
        }

        mBinding.btnCoroutineSwitchThread.setOnClickListener {
            coroutineSwitchThread()
        }
        mBinding.btnRetrofitSuspend.setOnClickListener {
            doRetrofitSuspendRequest()
        }

        mBinding.btnRetrofitCall.setOnClickListener {
            doRetrofitReturnCallRequest()
        }

        mBinding.btnRetrofitHandleException.setOnClickListener {
            doRetrofitHandleException()
        }

        mBinding.btnHandleException2.setOnClickListener {
            doHanleException()
        }

        mBinding.btnRxjavaSignle.setOnClickListener {
            doRxjavaSingleRequest()
        }

        mBinding.btnRxjavaZip.setOnClickListener {
            rxZip()
        }
        mBinding.btnCouroutineZip.setOnClickListener {
            coroutineAsyncWait()
        }

        mBinding.btnAwaitAll.setOnClickListener {
            coroutineAwaitAll()
        }

        mBinding.btnCouroutineCanceJob.setOnClickListener {
            job?.let {
                it.cancel()
            }
        }
        mBinding.btnMainscope.setOnClickListener {
            //使用 MainScope 不用指定Dispatchers.IO了
            mainScope.launch {
                apiService.listReposKt("rengwuxian")
            }
        }

        mBinding.btnCoroutineScope.setOnClickListener {
            coroutineScopeRequest()
        }
        mBinding.btnAysncToSync.setOnClickListener {
            callbackToSuspend()
        }

        mBinding.btnMultiUsers.setOnClickListener {
            loadMultiUser()
        }
    }

    private fun loadMultiUser() {
        mainScope.launch {
            val userIds = arrayOf("jerry","zhangsan","lisi")
            val users = userIds.map { id ->
                viewModel.getUserInfoById(id)
            }
            println(users)
        }

    }
    private fun callbackToSuspend() {
        mainScope.launch {
            try {
                val result: BaseResponse<EmptyResponse> = viewModel.callbackToSuspend()
                println("result:${result}")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private fun coroutineScopeRequest() {
       viewModel.coroutineScopeRequest()
    }

    private fun doHanleException() {
        mainScope.launch {
            viewModel.getArticleById(21630)
        }
    }

    private fun doRetrofitHandleException() {
        lifecycleScope.launch {
            val response = apiService.listReposKtWithErrorHandle("rengwuxian")
            println("result :${response}")
            if (response.isSuccessful){
                val repo: List<Repo>? = response.body()
                println("success repo:$repo")

            }else{
                println("faile :${response.raw()}")
            }
        }
    }

    private fun suspendCoroutineUsage() {
        lifecycleScope.launch {
            mBinding.tvResult.apply {
                isInvisible = true
                text = "Hi everyone"

                awaitNextLayout()

                isVisible = true
                translationY = -height.toFloat()
                animate().translationY(0f)

            }

        }

    }


    val scope = CoroutineScope(Job() + (Dispatchers.Main))
    private fun jobDispatcher() {
        scope.launch {
            var topList = reporsitoryKt.loadTopArticleCo()
            println("thread:${Thread.currentThread()},topList size:${topList?.size}")
        }
    }

    private fun coroutineAwaitAll() {
        lifecycleScope.launch {
            val start = System.currentTimeMillis()
            val deferred = listOf(
                async { reporsitoryKt.testData1() },
                async { reporsitoryKt.testData2() }
            )
            val list = deferred.awaitAll()
            println("awaitAll list: ${list}, finish cost:${System.currentTimeMillis() - start} ms")
        }
    }

    private var job: Job? = null
    private val mainScope = MainScope()

    private val mNetworkApi:ArticleNetwork = ArticleNetwork.getInstance()

    private val reporsitoryKt =
        HomeReporsitoryKt(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())


    private fun coroutineAsyncWait() {
        job = lifecycleScope.launch {
            val deferredOne = async { reporsitoryKt.testData1() }
            val deferredTwo = async { reporsitoryKt.testData2() }
            val result1 = deferredOne.await()
            val result2 = deferredTwo.await()
            println("result1:${result1}, result2:${result2}")
        }
    }

    private fun rxZip() {
        val request1 = apiService.listReposRx("rengwuxian")
        val request2 = apiService.listReposRx("jerryliuinfo")

        Single.zip(request1, request2) { list1, list2 ->
            "list1:${list1[0].name}, list2:${list2[0].name}"
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ combinded ->
                println("combinded:${combinded}")
            }, {
                it.printStackTrace()
            })


    }

    private lateinit var apiService: ApiService

    private fun initRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(CustomGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        apiService = retrofit.create(ApiService::class.java)


    }

    private fun doRetrofitSuspendRequest() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = mNetworkApi.collect2(21630)
            response.exceptionOrNull()?.let {
                ToastUtils.showShort(it.message)
            }
            Logger.d("resp name: ${response},thread: ${Thread.currentThread().name}")
        }
    }

    private fun doRetrofitReturnCallRequest() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                mNetworkApi.collect(ARTICLE_ID)
            }
            response.exceptionOrNull()?.let {
                ToastUtils.showShort(it.message)
            }
            Logger.d("resp name: ${response},thread: ${Thread.currentThread().name}")

        }
    }

    private val ARTICLE_ID = 21630

    private fun doRxjavaSingleRequest() {
        apiService.listReposRx("jerryliuinfo")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("resp name: ${it[0].name}")
            }, {
                it.printStackTrace()
            })


    }

    private fun traditionalSwitchThread() {
        classicIoCode1 {
            uiCode1()
        }
        classicIoCode2 {
            uiCode2()
        }
        classicIoCode3 {
            uiCode3()
        }
    }

    private fun coroutineSwitchThread() {
        //GlobalScope.launc 创建的协程是在后台执行的
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }
    }

    suspend fun ioCode1() {
        //切到子线程
        withContext(Dispatchers.IO) {
            println("Coroutines Camp io1:${Thread.currentThread().name}")
        }

    }

    fun uiCode1() {
        println("Coroutines Camp ui1:${Thread.currentThread().name}")
    }

    suspend fun ioCode2() {
        //切到子线程
        withContext(Dispatchers.IO) {
            println("Coroutines Camp io2:${Thread.currentThread().name}")
        }
    }

    fun uiCode2() {
        println("Coroutines Camp ui2:${Thread.currentThread().name}")
    }

    suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            println("Coroutines Camp io3:${Thread.currentThread().name}")
        }
    }

    fun uiCode3() {
        println("Coroutines Camp ui3:${Thread.currentThread().name}")
    }


    private fun classicIoCode1(block: () -> Unit) {
        thread {
            println("classic io1: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }

    private fun classicIoCode2(block: () -> Unit) {
        thread {
            println("classic io2: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }

    private fun classicIoCode3(block: () -> Unit) {
        thread {
            println("classic io3: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }

    override fun initViewModel() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}

