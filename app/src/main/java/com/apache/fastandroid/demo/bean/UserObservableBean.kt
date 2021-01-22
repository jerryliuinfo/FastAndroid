package com.apache.fastandroid.demo.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.apache.fastandroid.BR
import java.util.*

/**
 * Created by Jerry on 2021/1/22.
 */
class UserObservableBean:BaseObservable(){

    var degree = ObservableField<String>()

    //需要加上Bindable注解，并且notify，ui才会更新
    @Bindable
    var name:String = ""
        set(value) {
            field = value
            //通过所有属性发生改变，会比notifyPropertyChanged耗性能
//            notifyChange()
            notifyPropertyChanged(BR.name)
        }

    @Bindable
    var age:Int = 0
        set(value) {
            field = value
            //通知界面age发生了改变
            notifyPropertyChanged(BR.age)
        }

    fun plus(){
        degree.set(degree.get()+1)
    }
}

