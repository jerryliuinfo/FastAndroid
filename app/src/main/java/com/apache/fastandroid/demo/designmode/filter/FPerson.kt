package com.apache.fastandroid.demo.designmode.filter

import com.google.gson.annotations.SerializedName

/**
 * Created by Jerry on 2023/4/9.
 */
data class FPerson(@SerializedName("mName") val name:String, val age:Int, val gender:String)
