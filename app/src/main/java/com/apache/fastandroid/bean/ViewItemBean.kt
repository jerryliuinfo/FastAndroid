package com.apache.fastandroid.bean

import androidx.fragment.app.Fragment

/**
 * Created by Jerry on 2020/10/31.
 */
data class ViewItemBean (val title:String? = "", val description:String? = "", val clazz: Class<out Fragment>? = null)