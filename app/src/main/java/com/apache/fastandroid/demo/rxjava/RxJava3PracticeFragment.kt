package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.rxjava.operator.RetryWithDelay
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
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_rxjava2.*
import kotlinx.android.synthetic.main.fragment_rxjava3.*
import kotlinx.android.synthetic.main.fragment_rxjava3_practice.*
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * Created by Jerry on 2021/9/9.
 */
open class RxJava3PracticeFragment:BaseFragment() {
    companion object{
        private const val TAG = "RxJava3DemoFragment"
        private const val LINE_SEPERATOR = "\n"
    }
    private val compositeDisposable = CompositeDisposable()

    override fun inflateContentView(): Int {
        return R.layout.fragment_rxjava3_practice
    }

    private lateinit var textview:TextView



    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        textview = findViewById(R.id.tv_rx_result)

        doSeach()

        btn_retry_when.setOnClickListener {
            retryWhen()
        }

    }


    private fun retryWhen() {
        dataFromNetWork("qury")
            .retryWhen(RetryWithDelay(3,500))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver());


            
    }


    private fun doSeach(){
        et_search.fromView()
            //过了 timeout 时间之后再发送数据
            .debounce(200,TimeUnit.MILLISECONDS)
            //对etKey[EditText]的监听操作 需要在主线程操作
            .subscribeOn(AndroidSchedulers.mainThread())

            .filter {
                if (it.isNullOrEmpty()){
                    textview.text = ""
                    return@filter false
                }else{
                    return@filter true
                }
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Logger.d("showLoading")
            }
            .switchMap<String> {
                dataFromNetWork(it).doOnError {
                    //handle error
                }.onErrorReturn {

                    //continuer dimension        in case of error also
                    return@onErrorReturn "load data error:${it.message}"
                }

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }


    private fun dataFromNetWork(query:String):Observable<String>{
        return Observable.just(true)
            .delay(1,TimeUnit.SECONDS)
            .map<String> {
                return@map query
            }.flatMap<String> {
                if (query.length < 5){
                    return@flatMap Observable.error<String> {
                        return@error IllegalArgumentException( "${query}'s  size :${query.length} is small than 5")
                    }
                }
                return@flatMap Observable.just("onsuccess:$query")
            }
    }


    fun getStringObserver(): Observer<in String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                textview.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                Logger.d("TAG,  onNext: $t")
                textview.text = ""

                textview.append(" onNext: value:${t}")
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
                Logger.d("TAG, onComplete")
            }
        }
    }

    fun EditText.fromView():Observable<String>{
        val subject = PublishSubject.create<String>()
        addTextChangedListener {
            Logger.d(TAG,"fromView afterTextChanged: ${it.toString()}")
            subject.onNext(it.toString())
        }

        return subject
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}