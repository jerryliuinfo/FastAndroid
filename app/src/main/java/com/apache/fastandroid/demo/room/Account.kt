package com.apache.fastandroid.demo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apache.fastandroid.demo.bean.Vegetable

/**
 * Created by Jerry on 2022/3/13.
 */
@Entity(tableName = "account" )
data class Account(
    var name:String,
    var description:String,
    @PrimaryKey
    @ColumnInfo(name = "id" )
    var id:Long,
    var age:Long = 0,
)
