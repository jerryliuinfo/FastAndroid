package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.rxjava.map.ArticleToVideoMapper
import com.apache.fastandroid.network.model.FakeToken
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.response.BaseResponse
import com.blankj.utilcode.util.ToastUtils
import com.chad.baserecyclerviewadapterhelper.entity.Person
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
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

        apiService = ApiEngine.createApiService()

        btn_basic_usage.setOnClickListener {
            apiService.loadTopArticleCo2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                                var data = it.data
                    NLog.d(TAG, "data size: %s",data.size)
                }, {
                    ToastUtils.showShort(it.message)
                })
        }

        btn_map_usage.setOnClickListener {
            apiService.loadTopArticleCo2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ArticleToVideoMapper.getInstance())
                .subscribe({
                    NLog.d(TAG, "data size: %s",it)
                }, {
                    ToastUtils.showShort(it.message)
                })
        }
        btn_zip_usage.setOnClickListener {
            var observable1 = apiService.loadTopArticleCo2().map(ArticleToVideoMapper.getInstance())
            var observable2 = apiService.loadHomeArticleCo2(1)

            Observable.zip(observable1,observable2,object:BiFunction<List<UserBean>, BaseResponse<HomeArticleResponse>, List<Person>>{
                override fun apply(
                    t1: List<UserBean>,
                    t2: BaseResponse<HomeArticleResponse>
                ): List<Person> {
                    return arrayListOf(Person("zhangsan", 18),Person("lisi", 20))
                }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    NLog.d(TAG, "person list: %s",it)
                },{
                    ToastUtils.showShort(it.message)
                })
        }

       /* btn_flatmap_usage.setOnClickListener {
            ApiEngine.getFakeApi().getFakeToken("faFakeThingke_auth_code")
                .flatMap { t -> return@flatMap ApiEngine.getFakeApi().getFakeData(t) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    NLog.d(TAG, "flat map result: %s",it)
                },{
                    ToastUtils.showShort(it.message)
                })
        }
*/
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
       /* tokenUpdated = false
        Observable.just(1).flatMap(
        object :Function<Int,Observable<FakeThing>>{
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