package com.apache.fastandroid.jetpack

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Created by Jerry on 2021/5/25.
 * 继承这个BaseViewModel一定要用public修饰，否则会报 can't create class viewmodel 的错误
 */
abstract class BaseRxjavaViewModel : BaseViewModel(),IPageName{

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }


}