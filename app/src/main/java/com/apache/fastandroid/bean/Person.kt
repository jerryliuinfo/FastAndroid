package com.apache.fastandroid.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Jerry on 2021/9/9.
 */
data class Person(val name:String, val age:Int? ): MultiItemEntity {

    override val itemType: Int
        get() = 2
}