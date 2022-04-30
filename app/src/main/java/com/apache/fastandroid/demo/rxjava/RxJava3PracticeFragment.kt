package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentRxjava3PracticeBinding
import com.apache.fastandroid.demo.bean.AuthToken
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.rxjava.operator.RetryWithDelay
import com.apache.fastandroid.network.model.FakeToken
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.response.BaseResponse
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.apache.fastandroid.util.AccessDenyException
import com.apache.fastandroid.util.NetworkException
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_rxjava3_practice.*
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit


/**
 * Created by Jerry on 2021/9/9.
 */
open class RxJava3PracticeFragment:BaseVBFragment<FragmentRxjava3PracticeBinding>(FragmentRxjava3PracticeBinding::inflate) {
    companion object{
        private const val TAG = "RxJava3DemoFragment"
        private const val LINE_SEPERATOR = "\n"
    }
    private val compositeDisposable = CompositeDisposable()


    private lateinit var tvResult:TextView



    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        tvResult = findViewById(R.id.tv_rx_result)

        doSeach()

      /*  btn_retry_when.setOnClickListener {
            retryWhen()
        }*/

        btn_net_request.setOnClickListener {
            pollingRequest()
        }
        btn_net_request_nocondition.setOnClickListener {
            pollingRequestWithCondition()
        }
        btn_net_error_retry.setOnClickListener {
            networkErrorRetry()
        }
        btn_login_token.setOnClickListener {
            loginVerifyToken()
        }
    }
    private fun loginVerifyToken() {
        Observable.just(1)
            .flatMap {
                if (token == null){
                    return@flatMap Observable.error(IllegalArgumentException("token is null"))
                }else{
                    return@flatMap RetrofitFactory.instance.fakeApi.getFakeData(FakeToken(token,false))
                }
            }.retryWhen(object :Function<Observable<out Throwable?>, Observable<*>>{
                override fun apply(t: Observable<out Throwable?>): Observable<*> {
                    return t.flatMap {
                        if (it is IllegalArgumentException){
                            return@flatMap Observable.timer(1,TimeUnit.SECONDS).flatMap {
                               return@flatMap RetrofitFactory.instance.fakeApi.getFakeData(FakeToken(token,false))
                                    .doOnNext {
                                        token = it.name
                                    }
                            }
                        }else{
                            return@flatMap Observable.error(Throwable("not token error:${it?.message}"))
                        }
                    }
                }

            }).map {
                return@map "${it.id}: ${it.name}"
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())

    }

    private fun networkErrorRetry() {
        var currentRetryCount:Long = 0
        val maxRetryCount = 5
        loadData()
            .retryWhen(object :Function<Observable<out Throwable?>, Observable<*>>{
                override fun apply(t: Observable<out Throwable?>): Observable<*> {
                    return t.flatMap {
                        if (it is NetworkException){
                            if (currentRetryCount < maxRetryCount){
                                currentRetryCount++
                                val waitRetryTime = 1000 + currentRetryCount* 1000

                                return@flatMap Observable.just(1).delay(waitRetryTime,TimeUnit.MILLISECONDS)
                            }else{

                                return@flatMap Observable.error(Throwable("重试次数已超过设置次数 = $currentRetryCount，即 不再重试"))

                            }
                        }else{
                            return@flatMap Observable.error(Throwable("发生了非网络异常（非I/O异常）"));

                        }
                    }
                }

            }) .map<String> {
                Logger.d("pollingRequestWithCondition map  ")
                return@map it.data.datas?.get(0)?.chapterName ?: ""
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    private var i = 0

    /**
     * 无限轮询
     */
    private fun pollingRequest() {
        val disposable =  Observable.interval(2,1,TimeUnit.SECONDS)
            .doOnNext {
                tvResult.append("第 ${it} 次轮询：")

                RetrofitFactory.instance.apiService.loadHomeArticleCo2(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        tvResult.append(it.data.datas[0].chapterName)
                        tvResult.append(LINE_SEPERATOR)
                    }

            }
            .subscribe {
                Logger.d("pollingRequest: ${it}")
            }
        compositeDisposable.add(disposable)

    }

    /**
     * 有条件轮询
     */
    private fun pollingRequestWithCondition() {
        var repeatWhenCount = 0
        val disposable =  RetrofitFactory.instance.apiService.loadHomeArticleCo2(1)
            .repeatWhen {
                it.flatMap {
                    if (++repeatWhenCount <= 2){
//                        return@flatMap Observable.just(repeatWhenCount).delay(1,TimeUnit.SECONDS)
                        return@flatMap Observable.just(repeatWhenCount)
                    }
                    return@flatMap Observable.error(Throwable("不轮询啦"))
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<String> {
                Logger.d("pollingRequestWithCondition map  ")
                return@map it.data.datas[0].chapterName
            }

        disposable.subscribe(getStringObserver())
    }


    private var retryWhenCount = 0
    private fun loadData():Observable<BaseResponse<HomeArticleResponse>>{
        if (++retryWhenCount >= 0){
//            throw NetworkException("network error")
            return Observable.error(NetworkException("network error"))
        }
        return RetrofitFactory.instance.apiService.loadHomeArticleCo2(1)
    }

    private fun onErrorResumeNext() {
        retryCount = 0
        token = null
        val observable: Observable<UserBean> = getUserInfo()
        observable.onErrorResumeNext(refreshTokenAndRetry(observable))//also use retryWhen to implement it
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<String> {
                return@map it.name
            }
            .subscribe(getStringObserver())

    }


    private fun retryWhen() {
        dataFromNetWork("qury")
            .retryWhen(RetryWithDelay(3,500))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver());
    }

    /**
     * 搜索框执行网络请求优化
     */
    private fun doSeach(){
        et_search.fromView()
            //过了 timeout 时间之后再发送数据
            .debounce(200,TimeUnit.MILLISECONDS)
            //对etKey[EditText]的监听操作 需要在主线程操作
            .subscribeOn(AndroidSchedulers.mainThread())

            .filter {
                if (it.isNullOrEmpty()){
                    tvResult.text = ""
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
                tvResult.text = ""
                Logger.d("onSubscribe  ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                Logger.d("TAG,  onNext: $t")

                tvResult.append(" onNext: value:${t}")
                tvResult.append(LINE_SEPERATOR)
            }

            override fun onError(e: Throwable) {
                tvResult.append(" onError: ${e.message}")
                tvResult.append(LINE_SEPERATOR)
                Logger.d(" onError: ${e.message}")
            }

            override fun onComplete() {
                tvResult.append(" onComplete")
                tvResult.append(LINE_SEPERATOR)
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

    private var retryCount = 0;

    private var token:String? = null

    private fun getUserInfo():Observable<UserBean>{
        Logger.d("getUserInfo -->")
        tvResult.append("start getUserInfo");

        if (token == null){
            return Observable.error(AccessDenyException("token invalid"))
        }
        return Observable.just(UserBean("zhangsan",19))
    }


    private  fun <T> refreshTokenAndRetry(toBeResumed: Observable<T>): Function<Throwable?, out Observable<out T>?>? {
        return object : Function<Throwable?, Observable<out T>?> {
            @Throws(Throwable::class)
            override fun apply(throwable: Throwable?): Observable<out T>? {
                return if (isHttp401Error(throwable)) {
                    createTokenObvervable().flatMap { //
//                        textview.append("refresh token success,token's validity is 10s\nResume last request")
                        Logger.d("refresh token success,token's validity is 10s\n" +
                                "Resume last request")

                        toBeResumed
                    }
                } else Observable.error(throwable)
                // re-throwa this error because it's not recoverable from here
            }

            fun isHttp401Error(throwable: Throwable?): Boolean {
                return throwable is AccessDenyException
            }
        }
    }

    open fun createTokenObvervable(): Observable<AuthToken> {
        return Observable.create<AuthToken> { emitter ->
            Logger.d("God!!! Token is out of date. nstart refresh token...... --->")
//            textview.append("God!!! Token is out of date. \nstart refresh token......");
            token = "asdfafea145tadsf"
            emitter.onNext(AuthToken(token!!))
            emitter.onComplete()
        }.subscribeOn(AndroidSchedulers.mainThread())


    }


}