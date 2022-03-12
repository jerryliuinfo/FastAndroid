package com.apache.fastandroid.artemis.ui.bean

import com.tesla.framework.kt.resIdToString

/**
 * Created by Jerry on 2020/11/11.
 */
data class PageModel(val sampleLayoutRes:Int, val titleStr:String, val practiceLayoutRes:Int = 0){

    constructor( sampleLayoutRes:Int,  titleResId:Int,  practiceLayoutRes:Int = 0):this(sampleLayoutRes,titleResId.resIdToString,practiceLayoutRes)
}