package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.network.model.FakeToken
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiService
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_rxjava.*


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoFragment:BaseFragment() {
    companion object{
        private const val TAG = "RxJavaDemoFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_rxjava
    }
    val cachedFakeToken = FakeToken(true)
    var tokenUpdated = false


    private lateinit var apiService: ApiService


    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        apiService = ApiEngine.getApiService()






        btn_retrywhen_usage.setOnClickListener {
           doRetrhWhen()
        }


        btn_destroy_token.setOnClickListener {
            cachedFakeToken.expired = true
            ToastUtils.showShort("token已销毁")
        }
    }

    @SuppressLint("CheckResult")
    private fun doRetrhWhen(){
        tokenUpdated = false
       /* Observable.just(1).flatMap(
        object :Function<Int, ObservableSource<FakeThing>>{
            @SuppressLint("CheckResult")
            override fun apply(t: Int): Observable<FakeThing> {
                if (cachedFakeToken.token == null){
                    return Observable.error(NullPointerException("token is null"))
                }else{
                    return ApiEngine.getFakeApi().getFakeData(cachedFakeToken)
                }
            }

        }).retryWhen(object :Function<Observable<Throwable>, ObservableSource<FakeToken>>{
            override fun apply(t: Observable<Throwable>): ObservableSource<FakeToken> {
                return t.flatMap (object:Function<Throwable,ObservableSource<FakeToken>>{
                    override fun apply(throwable: Throwable): ObservableSource<FakeToken> {
                        return if (throwable is IllegalArgumentException || throwable is NullPointerException) {
                            ApiEngine.getFakeApi().getFakeToken("fake_auth_code")
                                .doOnNext(Consumer<FakeToken> { fakeToken ->
                                    tokenUpdated = true
                                    cachedFakeToken.token = fakeToken.token
                                    cachedFakeToken.expired = fakeToken.expired
                                })
                        } else Observable.error(throwable)
                    }

                })
            }

        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var token = cachedFakeToken.token
                if (tokenUpdated) {
                    token += "(" + "已更新" + ")"
                }
                tv_result.setText(getString(R.string.got_token_and_data, token, it.id, it.name)
                )
            },{
                ToastUtils.showShort(it.message)
            })*/

    }
}