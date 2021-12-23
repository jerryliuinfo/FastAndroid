package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.orhanobut.logger.Logger
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava2.*
import kotlinx.android.synthetic.main.fragment_rxjava3.*
import java.util.concurrent.TimeUnit


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJava3DemoFragment:BaseFragment() {
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
        btn_zip.setOnClickListener {
            zip()
        }

        btn_concact.setOnClickListener {
            concat()
        }

        btn_delay.setOnClickListener {
            delay()
        }

        btn_merge.setOnClickListener {
           merge()
        }

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