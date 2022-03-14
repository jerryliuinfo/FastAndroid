package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiServiceKt
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.awaitNextLayout
import com.tesla.framework.ui.fragment.BaseVBFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_kotlin_couritine.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.lang.Exception
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineDemoFragment:BaseVBFragment<FragmentKotlinCouritineBinding>(FragmentKotlinCouritineBinding::inflate) {
    companion object{
        private const val TAG = "CoroutineDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        initRetrofit()

        viewBinding.btnSuspendCoroutine.setOnClickListener {
            suspendCoroutineUsage()
        }

        viewBinding.btnJobDispatcher.setOnClickListener {
            jobDispatcher()
        }
        viewBinding.btnTraditionalSwitchThread.setOnClickListener {
            traditionalSwitchThread()
        }
        btn_coroutine_switch_thread.setOnClickListener {
            coroutineSwitchThread()
        }
        btn_corotine_retrofit.setOnClickListener {
            doRetrofitRequest()
        }

        btn_corotine_retrofit.setOnClickListener {
            doRetrofitRequest()
        }
        btn_rxjava.setOnClickListener {
            doRxjavaRequest()
        }

        btn_rxjava_zip.setOnClickListener {
           rxZip()
        }
        viewBinding.btnCouroutineZip.setOnClickListener {
            coroutineZip()
        }

        viewBinding.btnAwaitAll.setOnClickListener {
            coroutineAwaitAll()
        }

        viewBinding.btnCouroutineCanceJob.setOnClickListener {
            job?.let {
                it.cancel()
            }
        }
        viewBinding.btnMainscope.setOnClickListener {
            //使用 MainScope 不用指定Dispatchers.IO了
            mainScope.launch {
                apiService.listReposKt("rengwuxian")
            }
        }

    }

    private fun suspendCoroutineUsage() {
        lifecycleScope.launch {
            viewBinding.tvResult.apply {
                isInvisible = true
                text = "Hi everyone"

                awaitNextLayout()

                isVisible = true
                translationY= - height.toFloat()
                animate().translationY(0f)

            }

        }

    }


    val scope = CoroutineScope(Job() +(Dispatchers.Main) )
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
            deferred.awaitAll()
            println("awaitAll:finish cost:${System.currentTimeMillis() - start} ms")
        }
    }

    private  var job: Job? = null
    private val mainScope = MainScope()


    private val reporsitoryKt = HomeReporsitoryKt(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())



    private fun coroutineZip() {
       job =  GlobalScope.launch(Dispatchers.IO) {
            try {
                //asyn 会创建一个有结果的协程，但是这个结果不是以返回值形式返回的，而是以
                val request1 = async { apiService.listReposKt("rengwuxian") }
                val request2 = async { apiService.listReposKt("jerryliuinfo") }
                // = 的右边会比左边先执行
                val text = "${request1.await()[0].name}.${request2.await()[1].name} "
                Logger.d(text)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }



        lifecycleScope.launch {
            val deferredOne = async { reporsitoryKt.testData1() }
            val deferredTwo = async { reporsitoryKt.testData2() }
            val result1 = deferredOne.await()
            val result2 = deferredTwo.await()
            println("result1:${result1}, result2:${result2}")
        }
    }

    private fun rxZip(){
        val request1 = apiService.listReposRx("rengwuxian")
        val request2 = apiService.listReposRx("jerryliuinfo")
        Single.zip(request1,request2) { list1, list2 ->
            "list1:${list1[0].name}, list2:${list2[0].name}"
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ combinded ->
            println("combinded:${combinded}")
        },{
            it.printStackTrace()
        })


    }

    private lateinit var apiService: ApiServiceKt

    private fun initRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(CustomGsonConverterFactory.create())
            .addConverterFactory(CustomGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(ApiEngine.okHttpClient)
            .build()
        apiService = retrofit.create(ApiServiceKt::class.java)
    }

    private fun doRetrofitRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val respName = apiService.listReposKt("jerryliuinfo")
                Logger.d("resp name: ${respName},thread: ${Thread.currentThread().name}")

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun doRxjavaRequest(){
        apiService.listReposRx("jerryliuinfo")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("resp name: ${it[0].name}")
            },{
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

    private fun coroutineSwitchThread(){
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

    suspend fun ioCode1(){
        //切到子线程
        withContext(Dispatchers.IO){
            println("Coroutines Camp io1:${Thread.currentThread().name}")
        }

    }
    fun uiCode1(){
        println("Coroutines Camp ui1:${Thread.currentThread().name}")
    }

    suspend fun ioCode2(){
        //切到子线程
        withContext(Dispatchers.IO){
            println("Coroutines Camp io2:${Thread.currentThread().name}")
        }
    }

    fun uiCode2(){
        println("Coroutines Camp ui2:${Thread.currentThread().name}")
    }

    suspend fun ioCode3(){
        withContext(Dispatchers.IO){
            println("Coroutines Camp io3:${Thread.currentThread().name}")
        }
    }
    fun uiCode3(){
        println("Coroutines Camp ui3:${Thread.currentThread().name}")
    }


    private fun classicIoCode1(block: () -> Unit){
        thread {
            println("classic io1: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }
    private fun classicIoCode2(block: () -> Unit){
        thread {
            println("classic io2: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }
    private fun classicIoCode3(block: () -> Unit){
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

