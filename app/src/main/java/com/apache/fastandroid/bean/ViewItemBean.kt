package com.apache.fastandroid.bean

import androidx.fragment.app.Fragment
import com.tesla.framework.ui.fragment.ABaseFragment
import java.io.Serializable

/**
 * Created by Jerry on 2020/10/31.
 */
data class ViewItemBean (val title:String? = "", val description:String? = "", val clazz: Class<out Fragment>? = null):Serializable