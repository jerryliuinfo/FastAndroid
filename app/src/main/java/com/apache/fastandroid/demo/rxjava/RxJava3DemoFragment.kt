package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.orhanobut.logger.Logger
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.kt.runOnUiThreadDelay
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava2.*
import kotlinx.android.synthetic.main.fragment_rxjava3.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * Created by Jerry on 2021/9/9.
 */
open class RxJava3DemoFragment:BaseFragment() {
    companion object{
        private const val TAG = "RxJava3DemoFragment"
        private const val LINE_SEPERATOR = "\n"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_rxjava3
    }

    private lateinit var textview:TextView



    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        textview = findViewById(R.id.tv_rx_result)
        btn_simple.setOnClickListener {
            normal()
        }
        btn_map.setOnClickListener {
           map()
        }
        btn_switch_map.setOnClickListener {
            switchMap()
        }
        btn_flat_map.setOnClickListener {
            flatMap()
        }
        btn_concact_map.setOnClickListener {
            concactMap()
        }
        btn_zip.setOnClickListener {
            zip()
        }

        btn_concact.setOnClickListener {
            concat()
        }


        btn_merge.setOnClickListener {
           merge()
        }
        btn_delay.setOnClickListener {
            delay()
        }
        btn_timer.setOnClickListener {
            timer()
        }
        btn_filter.setOnClickListener {
            filter()
        }
        btn_take.setOnClickListener {
            take()
        }
        btn_take_until.setOnClickListener {
            takeUntil()
        }

        btn_take_while.setOnClickListener {
            takeWhile()
        }
        btn_throttle_first.setOnClickListener {
            throttleFirst()
        }
        btn_throttle_last.setOnClickListener {
            throttleLast()
        }
        btn_buffer.setOnClickListener {
            buffer()
        }
        btn_debounce.setOnClickListener {
            debounce()
        }
    }

    private fun debounce() {
        getObservable().debounce(300,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    /**
     * buffer(count)将原来的observable按照参数count组成长度为count的集合，以集合的形式发射出去
     */
    private fun buffer() {
        Observable.fromArray("A","B","C","D","E","F","G","H").buffer(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<List<String>>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }


                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

                override fun onNext(t: List<String>) {
                    textview.append("onNext: size:${t.size}")
                    textview.append(LINE_SEPERATOR)
                    t.forEach {
                        textview.append("value: ${it} ")
                        textview.append(LINE_SEPERATOR)
                        Logger.d("onNext value: ${it}")
                    }
                    Logger.d("onNext: size:${t.size}")
                }

            })
    }

    /**
     * concatMap是按输入的顺序执行的，concatMap在处理下一个inner Observable前会等待上一个inner Observable执行的结束，
     * 同时concatMap和mergeMap一样不能取消inner Observable的执行。
     */
    private fun concactMap() {
        Observable.just(10, 20, 30).concatMap<String> { it ->
            return@concatMap Observable.just(it.toString() +"x").delay(Random.nextLong(2),TimeUnit.SECONDS,Schedulers.io())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    /**
     * 收集所有单个可观测值，并在单个数组中返回所有可观测值，而无需关心可观测的顺序。异步工作。
     */
    private fun flatMap() {
        Observable.just(1, 2, 3)
            .flatMap <String> { it:Int ->
                Logger.d("flatMap it:${it}")
                return@flatMap  Observable.just(it.toString() +"x").delay(Random.nextLong(5),TimeUnit.SECONDS,Schedulers.io())

            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }


    /**
     * 发出最新的 Observable 值，并取消先前的 Observable 值。
     * 例如 这里只会发出 3
     * switchMap会优先处理最近的inner Observable，与concatMap和mergeMap不同的是switchMap在执行下一个inner Observable前可以
     * 取消上一个inner Observable的执行，在Observable被取消后emit的 inner Observable会被丢弃（不包括最终的Observable）
     */
    private fun switchMap() {
        Observable.just(1, 2, 3)
            .switchMap<String> { it:Int ->
                Logger.d("switchMap it:${it}")
                return@switchMap Observable.just(it.toString() +"x").delay(Random.nextLong(10),TimeUnit.SECONDS,Schedulers.io())

            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    /**
     * :在某段时间内，只发送该段时间内第1次事件(假如一个按钮1秒内点了3次 ,第一次显示,后2次不显示
     */
    private fun throttleLast() {
        getObservable().throttleLast(500,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    /**
     * 在某段时间内，只发送该段时间内最后1次事件(假如一个按钮1秒内点了3次 ,最后第一次显示,前两次不显示)
     */
    private fun throttleFirst() {
        getObservable().throttleFirst(300, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())


    }

    private fun getObservable():Observable<String>{
        return Observable.create {
            Thread.sleep(0)
            it.onNext("1")
            it.onNext("2")
            Thread.sleep(505)
            it.onNext("3")
            Thread.sleep(99)
            it.onNext("4")
            Thread.sleep(400)
            it.onNext("5")
            it.onNext("6")
            Thread.sleep(305)
            it.onNext("7")
            Thread.sleep(510)
            it.onComplete()
        }
    }

    /**
     * 当condition == false 时，直接终止，当不满足条件时，直接终止
     */
    private fun takeWhile() {
        getStringObservable().zipWith(Observable.interval(0, 1, TimeUnit.SECONDS), { s:String, aLong:Long ->
            println("takeWhile zipWith s:${s}, along:${aLong}")
            return@zipWith s
        })
            .takeWhile {
            println("takeWhile it:${it}")
//            return@takeWhile it.toLowerCase().contains("honey")
            return@takeWhile !it.toLowerCase().contains("honey")
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())

    }

    private val userBean:UserBean ? = null

    private var timeout:Int = 1

    /**
     * Observable.takUtil(condition)，当condition == false的时候终止，不包含临界条件的item
     */
    private fun takeUntil() {
        timeout = 1
        runOnUiThreadDelay({
                           timeout = 2
        },10000)

        getStringObservable().zipWith(Observable.interval(0, 3, TimeUnit.SECONDS), { s:String, aLong:Long ->
            println("takeUntil zipWith s:${s}, along:${aLong}")
            return@zipWith s
        }).takeUntil(object :Predicate<String>{
            override fun test(t: String): Boolean {
                val result =  t.toLowerCase().contains("ABCDE") ||  timeout == 2
                println("takeUntil t: ${t}, timeout:${timeout}, result: ${result}")
                return result
            }

        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())

    }

    /**
     * take(long count): 最大反射个数
     */
    private fun take() {
        Observable.just("AA","BB","CC","DD","EE")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(3)
            .subscribe(getObserver())

    }

    private fun getStringObservable():Observable<String>{
        return  Observable.just("Aloha","Beta","Cupcake","Dougnut","Eclair", "Honeycomb", "Ice cream sandwitch")

    }
     fun getObserver(): Observer<in String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                textview.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                textview.append(" onNext: value:${t}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                textview.append(" onError: ${e.message}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                textview.append(" onComplete")
                textview.append(LINE_SEPERATOR)
                Logger.d("onComplete")
            }
        }
    }


    private fun filter() {
        Observable.just(1,2,3,4,5,6)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Int>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: Int) {
                    textview.append(" onNext: value:${t}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })

    }

    /**
     * 创建一个Observable，它在一个给定的延迟后发射一个特殊的值
     */
    private fun timer() {
        Observable.timer(5,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Long>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: Long) {
                    textview.append(" onNext: value:${t}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })
    }

    /**
     * 不保证顺序发送
     */
    private fun merge(){
        var observableA = Observable.fromArray( "A2", "A1","A3", "A4")
        var observableB = Observable.fromArray("B1", "B2", "B3")
        Observable.merge(observableA,observableB).subscribe(object :Observer<String>{
            override fun onSubscribe(d: Disposable) {
                textview.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                textview.append(" onNext: value:${t}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                textview.append(" onError: ${e.message}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                textview.append(" onComplete")
                textview.append(LINE_SEPERATOR)
                Logger.d("onComplete")
            }

        })

    }

    /**
     * 延迟一段指定的时间再发送来自Observable的发送结果,其实delay()的常规使用跟timer()一致，那区别在哪呢？
     * delay()是用于流中的操作，跟map()、flatMap()的级别是一样的。而timer()是用于创建Observable，跟just()、from()的级别是一样的
     */
    private fun delay(){
        val startTime = System.currentTimeMillis()

        Observable.just("Amit")
            .delay(4,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<String>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: String) {
                    Logger.d(" onNext cost time: ${System.currentTimeMillis() - startTime} ms")

                    textview.append(" onNext: value:${t}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })
    }

    /**
     * 按照顺序发射
     */
    private fun concat(){
        var observableA = Observable.fromArray("A1", "A2", "A3", "A4")
        var observableB = Observable.fromArray("B1", "B2", "B3")
        Observable.concat(observableA,observableB)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<String>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: String) {
                    textview.append(" onNext: value:${t}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })
    }


    private fun zip(){
        Observable.zip(getZip1Observable(),getZip2Observable(), BiFunction { t1:UserBean, t2:UserBean ->
            return@BiFunction mutableListOf<UserBean>().apply {
                add(t1)
                add(t2)
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<List<UserBean>>{
                override fun onSubscribe(d: Disposable) {
                    textview.text = ""
                    Logger.d("onSubscribe  ${d?.isDisposed}")
                }

                override fun onNext(t: List<UserBean>) {
                    textview.append(" onNext: value:${t[0].name}, ${t[1].name}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })
    }

    private fun getZip1Observable():Observable<UserBean>{
        return Observable.create<UserBean>{
            if (!it.isDisposed){
                it.onNext(UserBean("zip1 name", 20))
                it.onComplete()
            }
        }
    }

    private fun getZip2Observable():Observable<UserBean>{
        return Observable.create<UserBean>{
            if (!it.isDisposed){
                it.onNext(UserBean("zip2 name", 30))
                it.onComplete()
            }
        }
    }


    private fun normal(){
        Observable.just("basketabll", "footbal")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<String>{
                override fun onSubscribe(d: Disposable?) {
                    textview.text = ""

                    Logger.d("onSubscribe  ${d?.isDisposed}")
                }

                override fun onNext(value: String) {
                    textview.append(" onNext: value:$value")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $value")


                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }


            })
    }

    private fun map(){
        Observable.create<String> {
            if (!it.isDisposed){
                it.onNext("Hello")
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map UserBean(it, 18)
            }
            .subscribe(object :Observer<UserBean>{
                override fun onSubscribe(d: Disposable?) {
                    textview.text = ""
                    Logger.d("onSubscribe: ${d?.isDisposed}")
                }

                override fun onNext(t: UserBean) {
                    textview.append(" onNext: value:${t.name}, ${t.age}")
                    textview.append(LINE_SEPERATOR)
                }

                override fun onError(e: Throwable) {
                    textview.append(" onError: ${e.message}")
                    textview.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    textview.append(" onComplete")
                    textview.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }
            })
    }




}