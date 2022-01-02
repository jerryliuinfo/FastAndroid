package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.orhanobut.logger.Logger
import com.tesla.framework.kt.runOnUiThreadDelay
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_rxjava2.*
import kotlinx.android.synthetic.main.fragment_rxjava3.*
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * Created by Jerry on 2021/9/9.
 */
open class RxJava3OperatorDemoFragment:BaseFragment() {
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
        btn_distinct.setOnClickListener {
            distinct()
        }
        btn_distinct_until_change.setOnClickListener {
            distinctUntilChange()
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
        btn_interval.setOnClickListener {
            interval()
        }
        btn_skip.setOnClickListener {
            skip();
        }
        btn_defer.setOnClickListener {
            defer()
        }
        btn_last.setOnClickListener {
            last()
        }
        btn_publish_subject.setOnClickListener {
            publishSubject()
        }
        btn_single.setOnClickListener {
            single()
        }
        btn_from_iterable.setOnClickListener {
            flatMap2()
        }

    }

    /**
     * 只发射单个数据或错误事件
     * Single的SingleObserver中只有onSuccess、onError，并没有onComplete。这是 Single 跟其他四种被观察者最大的区别。其他的它的使用和Observable差不多
     */
    private fun single() {
        Single.create<String> {
            it.onSuccess("Hello")
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingerObserver())
    }

    /**
     * 去重 过滤掉重复的元素
     * 输出 AA,BB,CC,DD,EE
     * 
     */
    private fun distinct() {
       getStringObservable()
           .distinct()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(getStringObserver())
    }

    /**
     * 过滤掉连续重复的元素,不连续重复的是不过滤
     * 输出 AA,BB,AA,CC,DD,EE
     */
    private fun distinctUntilChange() {
        getStringObservable()
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    private fun publishSubject() {
        var source = PublishSubject.create<String>()
        source.subscribe(getStringObserver())
        source.onNext("A")
        source.onNext("B")
        source.onNext("C")
//        source.subscribe(getStringObserver2())
        source.onNext("D")
        source.onComplete()

    }

    /**
     * 发射最后一个元素，如果最后一个元素为空，则发送默认数据 "A1"
     */
    private fun last() {
        getStringObservable().last("A1")
//        Observable.empty<String>().last("A1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingerObserver())

    }

    private fun getSingerObserver():SingleObserver<String>{
        return object :SingleObserver<String>{
            override fun onSubscribe(d: Disposable) {
                textview.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }


            override fun onError(e: Throwable) {
                textview.append(" onError: ${e.message}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onSuccess(t: String?) {
                textview.append(" onNext: value:${t}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

        }
    }

    /**
     * Defer是延迟的意思。Observable.defer是通过延迟创建数据生产者(Observable)的方式推迟数据生产的时间。
     * 直到注册的时候才开始生产数据.
     *
     */
    private fun defer() {
     Observable.defer<String> {
            Observable.just<String>(
                getData()
            )
        }
         .doOnSubscribe {
             Logger.d("Subscribe") }
         .subscribe { s: Any ->
            Logger.d(
                "Consume Data :$s"
            )
        }

    }

    private fun getData():String{
        Logger.d("produce data Hello")
        return "Hello"
    }

    /**
     * 跳过前面 count 个数据反射
     */
    private fun skip() {
        getObservable().skip(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    private val disposables = CompositeDisposable()

    private fun interval() {
        Observable.interval(2,TimeUnit.SECONDS)
            .flatMap <Long>{
                if (it > 5){
                    return@flatMap Observable.error(IllegalArgumentException("invalid num"))
                }else{
                    return@flatMap Observable.just(it)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    /**
     *
     * debounce操作符是对源Observable间隔期产生的结果进行过滤，
     * 如果在这个规定的间隔期内没有别的结果产生，则将这个结果提交给订阅者，否则忽略该结果，原理有点像光学防抖.
     */
    private fun debounce() {
        getObservable().debounce(300,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    /**
     * buffer(count)将原来的observable按照参数count组成长度为count的集合，以集合的形式发射出去
     */
    private fun buffer() {
//        Observable.fromArray("A","B","C","D","E","F","G","H").buffer(3)
        Observable.fromArray("A","B","C","D","E","F","G","H").buffer(3,2)
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
            .subscribe(getStringObserver())
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
            .subscribe(getStringObserver())
    }
    private fun flatMap2() {
        Observable.just(arrayListOf(UserBean("zhangsan",1),UserBean("lisi",2)))
            .flatMap <UserBean>{
                return@flatMap Observable.fromIterable(it)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Logger.d("userBean:${it}")
            }

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
            .subscribe(getStringObserver())
    }

    /**
     * :在某段时间内，只发送该段时间内第1次事件(假如一个按钮1秒内点了3次 ,第一次显示,后2次不显示
     */
    private fun throttleLast() {
        getObservable().throttleLast(500,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    /**
     * 在某段时间内，只发送该段时间内最后1次事件(假如一个按钮1秒内点了3次 ,最后第一次显示,前两次不显示)
     */
    private fun throttleFirst() {
        getObservable().throttleFirst(300, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())


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
            .subscribe(getStringObserver())

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
            .subscribe(getStringObserver())

    }

    /**
     * take(long count): 最大反射个数, 输入 "AA","BB","CC"
     */
    private fun take() {
        Observable.just("AA","BB","CC","DD","EE")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(3)
            .subscribe(getStringObserver())

    }

    private fun getStringObservable():Observable<String>{
        return  Observable.just("AA","AA","BB","AA","CC","DD","EE")

    }
     fun getStringObserver(): Observer<in String> {
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
    fun getStringObserver2(): Observer<in String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Logger.d("second onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                textview.append("second onNext: value:${t}")
                textview.append(LINE_SEPERATOR)
                Logger.d(" second onNext: $t")
            }

            override fun onError(e: Throwable) {
                textview.append("second onError: ${e.message}")
                textview.append(LINE_SEPERATOR)
                Logger.d("second onError: ${e.message}")
            }

            override fun onComplete() {
                textview.append("second onComplete")
                textview.append(LINE_SEPERATOR)
                Logger.d("second onComplete")
            }
        }
    }

    fun getIntObserver(): Observer<in Long> {
        return object : Observer<Long> {
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
        }
    }

    private fun filter() {
        Observable.just(1,2,3,4,5,6)
            .filter {
                return@filter it % 2 == 0
            }
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
//                    textview.append(" onNext: value:$value")
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


    override fun onDestroy() {
        super.onDestroy()
        //don't emit after destroy
        disposables.clear()
    }


}