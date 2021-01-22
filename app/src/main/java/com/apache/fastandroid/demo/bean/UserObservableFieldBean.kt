package com.apache.fastandroid.demo.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.apache.fastandroid.BR

/**
 * Created by Jerry on 2021/1/22.
 */
class UserObservableFieldBean:BaseObservable(){
    var name = ObservableField<String>()
    var age = ObservableField<Int>()



    fun plus(){
        age.set(age.get()?.plus(1))
    }
}

