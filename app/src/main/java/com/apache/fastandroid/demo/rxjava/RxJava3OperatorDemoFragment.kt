package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.rxjava.operator.RetryWithDelay
import com.tesla.framework.component.logger.Logger
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
import kotlinx.android.synthetic.main.fragment_rxjava.*
import kotlinx.android.synthetic.main.fragment_rxjava2.*
import kotlinx.android.synthetic.main.fragment_rxjava3.*
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
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
    override fun getLayoutId(): Int {
        return R.layout.fragment_rxjava3
    }

    private lateinit var result:TextView



    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        result = findViewById(R.id.tv_rx_result)
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
        btn_interval_range.setOnClickListener {
            intervaRagne()
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

        btn_ranger.setOnClickListener {
            ranger()
        }
        btn_fromArray.setOnClickListener {
            fromArray()
        }
        btn_fromIterable.setOnClickListener {
            fromIterable()
        }
        btn_repeat.setOnClickListener {
            repeatSeperator()
        }
        btn_repeat_when.setOnClickListener {
            repeatWhen()
        }
        btn_do.setOnClickListener {
            doSeperator()
        }
        btn_onErrorReturn.setOnClickListener {
            onErrorReturn()
        }
        btn_onErrorResumeNext.setOnClickListener {
            onErrorResumeNext()
        }
        btn_retry.setOnClickListener {
            retry()
        }
        btn_retry_times.setOnClickListener {
            retryWithTime()
        }
        btn_retry_predicate.setOnClickListener {
            retryWithPredicate()
        }
        btn_retry_predicate2.setOnClickListener {
            retryWithTimesAndPredicate()
        }
        btn_retry_until.setOnClickListener {
            retryUntil()
        }
        btn_retry_until.setOnClickListener {
            retryUntil()
        }
        btn_retry_when.setOnClickListener {
            retryWhen()
        }
    }



    /**
     *  作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
     *  返回false = 不重新重新发送数据 & 调用观察者的onError结束
     *  返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
     *
     */
    private fun retryWithPredicate() {
        getErrorObservable()
            //拦截错误后，判断是否需要重新发送请求
            .retry { retryCount: Int, throwable: Throwable? ->
                Logger.d("retryWithPredicate retryCount:${retryCount}")
            return@retry retryCount <= 1
        }.subscribe(getIntObserver())

    }

    /**
     * 出现错误后，判断是否需要重新发送数据（具备重试次数限制
     * 参数 = 设置重试次数 & 判断逻辑
     * 如果返回true，则代表需要重试，但是会有重试次数限制
     */
    private fun retryWithTimesAndPredicate() {
        getErrorObservable()
            .retry(2){
                // 返回false = 不重新重新发送数据 & 调用观察者的onError（）结束(不会重试 )
                // 返回true = 重新发送请求（最多重新发送3次）
                return@retry true
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())

    }

    /**
     * 出现错误后，判断是否需要重新发送数据
     * 1.若需要重新发送 & 持续遇到错误，则持续重试
     * 2.作用类似于retry（Predicate predicate）
     * 具体使用类似于retry（Predicate predicate），
     * 唯一区别：
     * 对于 retryUntil: 返回 true 代表 不需要 重新发送数据事件
     * 对于 retry:      返回 true 代表 重新 发送数据事件
     */
    private fun retryUntil() {
        var retryUntilCount = 0
        getErrorObservable()
            .retryUntil {
                //返回 true 则不重新发送数据事件, 返回false，则重新发送
                val retry = ++retryUntilCount >1
                return@retryUntil retry
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    /**
     * 遇到错误时，将发生的错误传递给一个新的被观察者（Observable），
     * 并决定是否需要重新订阅原始被观察者（Observable）& 发送事件
     */
    private fun retryWhen(){
        getErrorObservable()
            .retryWhen(RetryWithDelay(1,1000))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    /**
     * 遇到错误时，发送1个特殊事件 & 正常终止
     * 可捕获在它之前发生的异常
     * 发射:1、2、666、onComplete
     */
    private fun onErrorReturn() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("发生错误了"))

        }.onErrorReturn {
            //发生错误事件后，发送一个"666"事件，最终正常结束
            return@onErrorReturn 666
        }.subscribe(getIntObserver())
    }

    /**
     * 遇到错误时，发送1个新的Observable
     *
     * onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
      若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法


     */
    private fun onErrorResumeNext(){
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(Throwable("发生错误了"))
        }.onErrorResumeNext {
            Logger.d( "在onErrorReturn处理了错误: "+it.toString() );

            // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
             Observable.just(11,22);
        }.subscribe(getIntObserver())
    }


    /**
     * 重试，即当出现错误时，让被观察者（Observable）重新发射数据
     * 接收到 onError（）时，重新订阅 & 发送事件
     * Throwable 和 Exception都可拦截
     * 共有5种重载方法
     * <-- 1. retry（） -->
        // 作用：出现错误时，让被观察者重新发送数据
        // 注：若一直错误，则一直重新发送

        <-- 2. retry（long time） -->
        // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
        // 参数 = 重试次数

        <-- 3. retry（Predicate predicate） -->
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
        // 参数 = 判断逻辑

        <--  4. retry（new BiPredicate<Integer, Throwable>） -->
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
        // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）

        <-- 5. retry（long time,Predicate predicate） -->
        // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
        // 参数 = 设置重试次数 & 判断逻辑

     */
    private fun retry() {
        getErrorObservable()
            .retry()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    private fun retryWithTime() {
        getErrorObservable()
            .retry(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    private fun getErrorObservable():Observable<Int>{
        return Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(2000)
            it.onNext(2)
            Thread.sleep(2000)

            it.onError(Throwable("发生错误了"))
            it.onNext(3)
        }.subscribeOn(Schedulers.io())
    }

    private fun doSeperator() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onError(RuntimeException("发生错误了"))
        }.doOnEach {
            result.append("doonEach: ${it.value}")
            result.append(LINE_SEPERATOR)
        }
            // 执行Next事件前调用
            .doOnNext {
            result.append("doOnNext: ${it}")
            result.append(LINE_SEPERATOR)
        }
                //执行Next事件后调用
            .doAfterNext {
                result.append("doAfterNext: ${it}")
                result.append(LINE_SEPERATOR)
            }
            .doOnComplete {
                result.append("doOnComplete")
                result.append(LINE_SEPERATOR)
            }
            .doOnError {
                result.append("doOnError:${it.message}")
                result.append(LINE_SEPERATOR)
            }
            .doOnSubscribe {
                result.append("doOnSubscribe:${it.isDisposed}")
                result.append(LINE_SEPERATOR)
            }.doAfterTerminate {
                result.append("doAfterTerminate")
                result.append(LINE_SEPERATOR)
            }.doOnTerminate {
                result.append("doOnTerminate")
                result.append(LINE_SEPERATOR)
            }.subscribe(getIntObserver())
    }

    /**
     * 无条件地、重复发送 被观察者事件
     * 具备重载方法，可设置重复创建次数
     * 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable），
     * 以此决定是否重新订阅 & 发送原来的 Observable
     * 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
       若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable
     */
    private fun repeatSeperator() {
        Observable.just("A","B")
            .repeat(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }


    private var repeatWhenCount = 0
    /**
     * 有条件地、重复发送 被观察者事件
     * 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable），以此决定是否重新订阅 & 发送原来的 Observable
        若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
        若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable

     *
     */
    private fun repeatWhen() {
        Observable.just("A","B")
            .repeatWhen {
                it.flatMap<Boolean> {
                    if (++repeatWhenCount >= 2){
                        return@flatMap Observable.empty<Boolean>()
                    }
                    return@flatMap Observable.just(true)

                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    /**
     * 发送数组的数据
     * 分三次分别发送  1、2、3数据
     */
    private fun fromArray() {
        Observable.fromIterable(arrayListOf(1,2,3))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    /**
     * 直接发送传入的集合list数据
     */
    private fun fromIterable() {
        Observable.fromArray(1,2,3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getIntObserver())
    }

    /**
     * 连续发送一个事件序列，可指定范围
     */
    private fun ranger() {
        Observable.range(3,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result.append(" onNext: value:${it}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onNext: $it")
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
                result.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }


            override fun onError(e: Throwable) {
                result.append(" onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onSuccess(t: String?) {
                result.append(" onNext: value:${t}")
                result.append(LINE_SEPERATOR)
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

    /**
     * 无限次轮询:每隔指定的时间就发送事件，发送的事件序列 = 从 0 开始，无线递增 1 的整数序列
     */
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
            .subscribe(getLongObserver())
    }

    /**
     * 有限次轮询:每隔指定的时间就发送事件，可指定发送的数据的数量
     *
     */
    private fun intervaRagne() {
        Observable.intervalRange(1,3,0,1,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getLongObserver())
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
                    result.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }


                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

                override fun onNext(t: List<String>) {
                    result.append("onNext: size:${t.size}")
                    result.append(LINE_SEPERATOR)
                    t.forEach {
                        result.append("value: ${it} ")
                        result.append(LINE_SEPERATOR)
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
                result.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                result.append(" onNext: value:${t}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                result.append(" onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                result.append(" onComplete")
                result.append(LINE_SEPERATOR)
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
                result.append("second onNext: value:${t}")
                result.append(LINE_SEPERATOR)
                Logger.d(" second onNext: $t")
            }

            override fun onError(e: Throwable) {
                result.append("second onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d("second onError: ${e.message}")
            }

            override fun onComplete() {
                result.append("second onComplete")
                result.append(LINE_SEPERATOR)
                Logger.d("second onComplete")
            }
        }
    }

    fun getLongObserver(): Observer<in Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                result.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: Long) {
                result.append(" onNext: value:${t}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                result.append(" onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                result.append(" onComplete")
                result.append(LINE_SEPERATOR)
                Logger.d("onComplete")
            }
        }
    }

    fun getIntObserver(): Observer<in Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                result.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: Int) {
                result.append(" onNext: value:${t}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                result.append(" onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                result.append(" onComplete")
                result.append(LINE_SEPERATOR)
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
                    result.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: Int) {
                    result.append(" onNext: value:${t}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })

    }

    /**
     * 创建一个Observable，它在一个给定的延迟后发射一个0（调用一次 onNext(0)）
     */
    private fun timer() {
        Observable.timer(3,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Long>{
                override fun onSubscribe(d: Disposable) {
                    result.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: Long) {
                    result.append(" onNext: value:${t}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
                    Logger.d("onComplete")
                }

            })
    }

    /**
     * 不保证顺序发送
     */
    private fun merge(){
        var observableA = Observable.fromArray( "网络")
        var observableB = Observable.fromArray("本地")
        Observable.merge(observableA,observableB)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<String>{
            override fun onSubscribe(d: Disposable) {
                result.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                result.append(" onNext: value:${t}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onNext: $t")
            }

            override fun onError(e: Throwable) {
                result.append(" onError: ${e.message}")
                result.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                result.append(" onComplete")
                result.append(LINE_SEPERATOR)
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
                    result.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: String) {
                    Logger.d(" onNext cost time: ${System.currentTimeMillis() - startTime} ms")

                    result.append(" onNext: value:${t}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
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
                    result.text = ""
                    Logger.d("onSubscribe  ${d.isDisposed}")
                }

                override fun onNext(t: String) {
                    result.append(" onNext: value:${t}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
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
                    result.text = ""
                    Logger.d("onSubscribe  ${d?.isDisposed}")
                }

                override fun onNext(t: List<UserBean>) {
                    result.append(" onNext: value:${t[0].name}, ${t[1].name}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $t")
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
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
                    result.text = ""

                    Logger.d("onSubscribe  ${d?.isDisposed}")
                }

                override fun onNext(value: String) {
//                    textview.append(" onNext: value:$value")
                    result.append(" onNext: value:$value")

                    result.append(LINE_SEPERATOR)
                    Logger.d(" onNext: $value")


                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
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
                    result.text = ""
                    Logger.d("onSubscribe: ${d?.isDisposed}")
                }

                override fun onNext(t: UserBean) {
                    result.append(" onNext: value:${t.name}, ${t.age}")
                    result.append(LINE_SEPERATOR)
                }

                override fun onError(e: Throwable) {
                    result.append(" onError: ${e.message}")
                    result.append(LINE_SEPERATOR)
                    Logger.d(" onError: ${e.message}")
                }

                override fun onComplete() {
                    result.append(" onComplete")
                    result.append(LINE_SEPERATOR)
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