package com.fastframework.plugin

/**
 * Created by Jerry on 2022/2/2.
 * 这里由于需要写成配置的方式，所以要用 var 而不是 val
 *
 */
open class ReleaseInfoNew(var versionName:String = "",var versionCode:String = "",var versionInfo:String ="", var fileName:String = "")