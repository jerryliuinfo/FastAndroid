package com.apache.fastandroid.demo.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jerry on 2022/2/3.
 */

/*
@Entity(tableName = "user")
data class User (
    // 主键分配自动ID
    // @PrimaryKey(autoGenerate = true) val uid: Int,
    @PrimaryKey var uId:Int,
    @ColumnInfo(name = "fistName")  var fistName:String?,
    @ColumnInfo(name = "lastName")  var lastName:String?,
){
    constructor():this(0,"","")
    constructor(fistName: String?,lastName: String?):this(0,fistName,lastName)
}*/
