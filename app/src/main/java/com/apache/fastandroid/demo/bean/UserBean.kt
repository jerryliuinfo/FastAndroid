package com.apache.fastandroid.demo.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import java.io.Serializable
import java.util.*

/**
 * Created by Jerry on 2021/1/22.
 */
 class UserBean( var name:String?,  var age:Int? = 10):Serializable{
  operator fun component1() = name
  operator fun component2() = age
  operator fun component3() = name
}

