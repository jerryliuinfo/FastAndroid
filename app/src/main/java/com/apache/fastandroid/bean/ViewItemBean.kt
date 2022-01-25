package com.apache.fastandroid.bean

import android.app.Activity
import androidx.fragment.app.Fragment
import com.tesla.framework.ui.activity.BaseVmActivity
import java.io.Serializable

/**
 * Created by Jerry on 2020/10/31.
 */
data class ViewItemBean (var title:String, val description:String = "",
                         val clazz: Class<out Fragment>? = null,
                         val activity: Class<out Activity>? = null):Serializable{

    constructor(title: String,clazz: Class<out Fragment>?): this(title,title,clazz)

}