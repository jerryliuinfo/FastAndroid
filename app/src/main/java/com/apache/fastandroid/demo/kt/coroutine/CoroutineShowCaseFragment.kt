package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.article.ArticleNetwork
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.model.result.BaseResponse
import com.apache.fastandroid.network.model.result.EmptyResponse
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.awaitNextLayout
import com.tesla.framework.ui.fragment.BaseBindingFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineShowCaseFragment :
    BaseBindingFragment<FragmentKotlinCouritineBinding>(FragmentKotlinCouritineBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineDemoListFragment"
    }

    private val viewModel: CoroutineVewModel by viewModels()

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
            // 使用 MainScope 不用指定Dispatchers.IO了
            mainScope.launch {
                val list = apiService.listReposKt("rengwuxian")
                showUi(list)
            }
        }

        mBinding.btnCoroutineScope.setOnClickListener {
            coroutineScopeRequest()
        }

        mBinding.btnEnsureCoroutineActive.setOnClickListener {
            isCoroutineActive()
        }

        mBinding.btnCancelCoroutine.setOnClickListener {

        }
        mBinding.btnAysncToSync.setOnClickListener {
            callbackToSuspend()
        }

        mBinding.btnMultiUsers.setOnClickListener {
            loadMultiUser()
        }
    }

    private fun isCoroutineActive() {
        var job:Job ?= null
        lifecycleScope.launch {
            job = lifecycleScope.launch {
                println("do something 1")
                delay(20000)
                println("do something 2")
            }

            delay(500)
            job?.cancel()
            println("do something 3, active:${job?.isActive}")

        }


    }

    private fun showUi(list: List<Repo>) {

    }

    private fun loadMultiUser() {
        mainScope.launch {
            val userIds = arrayOf("jerry", "zhangsan", "lisi")
            val userInfoJobs:List<Deferred<UserBean>> = userIds.map { userId ->
                async {
                    viewModel.getUserInfoById(userId)
                }
            }
            // 使用awaitAll等待所有Deferred完成
            val userInfoList = userInfoJobs.awaitAll()
            println("loadMultiUser userInfoList:$userInfoList")
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
            if (response.isSuccessful) {
                val repo: List<Repo>? = response.body()
                println("success repo:$repo")

            } else {
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
            val topList = reporsitoryKt.loadTopArticleCo()
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

    private val mNetworkApi: ArticleNetwork = ArticleNetwork.getInstance()

    private val reporsitoryKt =
        HomeReporsitoryKt(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())


    /**
     * 并行执行请求
     */
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
        lifecycleScope.launch(Dispatchers.Main) {
            val response = mNetworkApi.collect2(21630)
            response.exceptionOrNull()?.let {
                ToastUtils.showShort(it.message)
            }
            Logger.d("resp name: ${response},thread: ${Thread.currentThread().name}")
        }
    }

    private fun doRetrofitReturnCallRequest() {
        lifecycleScope.launch(Dispatchers.Main) {
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
        // GlobalScope.launc 创建的协程是在后台执行的
        GlobalScope.launch(Dispatchers.Main) {
            val result1 = ioCode1()
            uiCode1(result1)
            val result2 = ioCode2()
            uiCode2(result2)
            ioCode3()
            uiCode3()
        }
    }

    suspend fun ioCode1(): String = withContext(Dispatchers.IO) {
        // 切到子线程
        println("Coroutines Camp io1:${Thread.currentThread().name}")
        delay(500)
        "result1"
    }

    fun uiCode1(str: String? = null) {
        println("Coroutines Camp ui1:${Thread.currentThread().name}")
        mBinding.tvResult.text = str
    }

    suspend fun ioCode2(): String = withContext(Dispatchers.IO) {
        // 切到子线程
        println("Coroutines Camp io2:${Thread.currentThread().name}")
        delay(1500)
        "result2"
    }

    fun uiCode2(result: String? = null) {
        println("Coroutines Camp ui2:${Thread.currentThread().name}")
        mBinding.tvResult.text = result

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

