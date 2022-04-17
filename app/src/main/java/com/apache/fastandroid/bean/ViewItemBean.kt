package com.apache.fastandroid.bean

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by Jerry on 2020/10/31.
 */
data class ViewItemBean (var title:String, val description:String = "",
                         val clazz: Class<out Fragment>? = null,
                         val activity: Class<out Activity>? = null,val args:Bundle?= null,val addTitleBar:Boolean= true):Serializable{

    constructor(title: String,clazz: Class<out Fragment>?): this(title,title,clazz)

}