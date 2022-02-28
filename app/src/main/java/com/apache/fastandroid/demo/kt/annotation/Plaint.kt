package com.apache.fastandroid.demo.kt.annotation

/**
 * Created by Jerry on 2022/2/27.
 */
class Plaint {
    @get:OnGet
    val isGrowing:Boolean = true

    @set:OnSet
    var needsFood:Boolean = true
}
