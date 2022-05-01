package com.apache.fastandroid.demo.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * Created by Jerry on 2021/1/22.
 */

data class UserBean( var name:String?,  var age:Int = 10):Serializable
