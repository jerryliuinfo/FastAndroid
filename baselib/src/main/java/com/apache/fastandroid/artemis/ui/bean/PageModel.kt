package com.apache.fastandroid.artemis.ui.bean

import com.tesla.framework.common.util.ResUtil

/**
 * Created by Jerry on 2020/11/11.
 */
data class PageModel(val sampleLayoutRes:Int, val titleRes:String, val practiceLayoutRes:Int){
    constructor( sampleLayoutRes:Int,  titleRes:Int,  practiceLayoutRes:Int):this(sampleLayoutRes,ResUtil.getString(titleRes),practiceLayoutRes)
}