package com.apache.fastandroid.demo.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField

/**
 * Created by Jerry on 2021/1/22.
 */
class UserObservableFieldBean:BaseObservable(){
    var name:Observable = ObservableField<String>("")
        get() {
            return name
        }

    var age:Observable = ObservableField(0)




    fun plus(){
    }
}

