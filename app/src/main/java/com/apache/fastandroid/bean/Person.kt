package com.apache.fastandroid.bean

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jerry on 2021/9/9.
 */
@Parcelize
data class Person(val name:String, val age:Int? ): MultiItemEntity, Parcelable {

    override val itemType: Int
        get() = 2
}