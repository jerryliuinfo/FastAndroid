package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava2.*


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoFragment2:BaseFragment() {
    companion object{
        private const val TAG = "RxJavaDemoFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_rxjava2
    }

    private val stringBuilder = StringBuilder()


    @SuppressLint("CheckResult")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        btn_create_usage.setOnClickListener {
           createOperator()
        }

        btn_map_operator_usage.setOnClickListener {
            mapOperator()
        }

        btn_zip_operator_usage.setOnClickListener {
            zipOperator()
        }

        btn_concact_usage.setOnClickListener {
            //的把两个发射器连接成一个发射器
            concactUsage()
        }

        btn_flatmap_usage.setOnClickListener {
            flatMapUsage()
        }



        btn_concactMap_usage.setOnClickListener {
            concactMapUsage()
        }

        btn_distinct_usage.setOnClickListener {
            distinctUsage()
        }

        btn_filter_usage.setOnClickListener {
            filterUsage()
        }
    }

    /**
     * 过滤
     */
    @SuppressLint("CheckResult")
    private fun filterUsage(){
        stringBuilder.clear()
        Observable.just(1,4,1,2,3,2,4,5).distinct().filter {
            it % 2 == 0
        } .subscribe {
                NLog.d(TAG, "it: %s", it)
                stringBuilder.append("$it \n")
                tv_rx_result.text = stringBuilder.toString()

            }
    }

    /**
     * 去重
     */
    private fun distinctUsage(){
        stringBuilder.clear()
        Observable.just(1,4,1,2,3,2,4,5).distinct()
            .subscribe({
                NLog.d(TAG, "it: %s",it)
                stringBuilder.append("$it \n")
                tv_rx_result.text = stringBuilder.toString()

            })
    }

    private fun flatMapUsage(){
        stringBuilder.clear()
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onComplete()
        }.flatMap<String> {
            return@flatMap Observable.just("abc: $it")
        }.subscribe({
            stringBuilder.append("$it \n")
        },{},{
            tv_rx_result.text = stringBuilder.toString()
        })
    }

    private fun concactMapUsage(){
        stringBuilder.clear()
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onComplete()
        }.concatMap {
            Observable.just("abc: $it")
        }.subscribe({
            stringBuilder.append("$it \n")
        },{},{
            tv_rx_result.text = stringBuilder.toString()
        })
    }


    /**
     * 把两个发射器连接成一个发射器
     */
    private fun concactUsage() {
        stringBuilder.clear()
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
            .subscribe({
                stringBuilder.append("concact:${it}" + "\n")
            }, {}, {
                tv_rx_result.text = stringBuilder.toString()
            })
    }

    @SuppressLint("CheckResult")
    private fun zipOperator(){
        stringBuilder.clear()
        val observable1 = Observable.create<Int>{
            stringBuilder.append("Observable1 emit 1" + "\n");
            it.onNext(1)
            stringBuilder.append("Observable1 emit 2" + "\n");
            it.onNext(2)

            it.onNext(3)
            it.onComplete()
        }
        val observable2 = Observable.create<String>{
            stringBuilder.append("Observable2 emit A" + "\n");
            it.onNext("A")
            stringBuilder.append("Observable2 emit B" + "\n");
            it.onNext("B")
            it.onComplete()
        }
        //这里导包务必记得导io.reactivex.functions.BiFunction,不要导成java.util.function.BiFunction
        Observable.zip(observable1,observable2,object: BiFunction<Int, String, UserBean> {
            override fun apply(t1: Int, t2: String): UserBean {
                return UserBean(t2,t1)
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                stringBuilder.append("onNext: ${it} \n")
            },{

            },{
                stringBuilder.append("onComplete \n")
                tv_rx_result.text = stringBuilder.toString()
            })
    }

  /*  subscribe({
        stringBuilder.append("onNext: ${it} \n")
    },{
        NLog.d(TAG, "on Error dismiisLoading")
        stringBuilder.append("onError: ${it.message}  \n")
    },{
        stringBuilder.append("onComplete \n")
        tv_rx_result.text = stringBuilder.toString()
    },{
        NLog.d(TAG, "onSubscribe")
        stringBuilder.append("onSubscribe isDiposed: ${it.isDisposed}\n")
    })*/

    @SuppressLint("CheckResult")
    private fun createOperator(){
        stringBuilder.clear()
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(e: ObservableEmitter<Int>) {
                stringBuilder.append("Observable emit 1" + "\n");
                e.onNext(1);
                stringBuilder.append("Observable emit 2" + "\n");
                e.onNext(2);
                stringBuilder.append("Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                stringBuilder.append("Observable emit 4" + "\n");
                e.onNext(4);
            }

        }).subscribe{
            stringBuilder.append("onNext: ${it} \n")
        }
    }

    @SuppressLint("CheckResult")
    private fun mapOperator(){
        stringBuilder.clear()
        Observable.create<Int> {
            stringBuilder.append("Observable emit 1" + "\n");
            it.onNext(1);
            stringBuilder.append("Observable emit 2" + "\n");
            it.onNext(2);
            stringBuilder.append("Observable emit 3" + "\n");
            it.onNext(3);
            it.onComplete();
        }.map {
            return@map "map to $it"
        }
            .subscribe{
                stringBuilder.append("onNext: ${it} \n")
            }
    }
}