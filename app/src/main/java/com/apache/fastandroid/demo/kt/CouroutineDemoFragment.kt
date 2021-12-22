package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiServiceKt
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.orhanobut.logger.Logger
import com.tesla.framework.ui.fragment.BaseLifecycleFragment
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
class CouroutineDemoFragment:BaseLifecycleFragment<FragmentKotlinCouritineBinding>() {
    companion object{
        private const val TAG = "CouroutineDemoFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_kotlin_couritine
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        initRetrofit()
        btn_traditional_switch_thread.setOnClickListener {
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
        btn_couroutine_zip.setOnClickListener {
            coroutineZip()
        }
        btn_couroutine_cance_job.setOnClickListener {
            job?.let {
                it.cancel()
            }
        }
        btn_mainscope.setOnClickListener {
            //使用 MainScope 不用指定Dispatchers.IO了
            mainScope.launch {
                apiService.listReposKt("rengwuxian")
            }
        }

    }

    private  var job: Job? = null
    private val mainScope = MainScope();



    private fun coroutineZip() {
       job =  GlobalScope.launch(Dispatchers.IO) {
            try {
                val request1 = async { apiService.listReposKt("rengwuxian") }
                val request2 = async { apiService.listReposKt("jerryliuinfo") }
                Logger.d("${request1.await()[0].name}.${request2.await()[1].name} ")
            }catch (e:Exception){
                e.printStackTrace()
            }
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
            .client(ApiEngine.getOkHttpClient())
            .build()
        apiService = retrofit.create(ApiServiceKt::class.java)
    }

    private fun doRetrofitRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val respName = apiService.listReposKt("jerryliuinfo")
                Logger.d("resoname: ${respName}")

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun doRxjavaRequest(){
        apiService.listReposRx("jerryliuinfo")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.d("resoname: ${it[0].name}")
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