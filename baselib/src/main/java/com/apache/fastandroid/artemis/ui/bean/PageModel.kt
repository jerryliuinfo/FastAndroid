package com.apache.fastandroid.artemis.ui.bean

import com.blankj.utilcode.util.ResourceUtils

/**
 * Created by Jerry on 2020/11/11.
 */
data class PageModel(val sampleLayoutRes:Int, val titleStr:String, val practiceLayoutRes:Int){

    constructor( sampleLayoutRes:Int,  titleResId:Int,  practiceLayoutRes:Int):this(sampleLayoutRes,ResourceUtils.getString(titleResId),practiceLayoutRes)
}