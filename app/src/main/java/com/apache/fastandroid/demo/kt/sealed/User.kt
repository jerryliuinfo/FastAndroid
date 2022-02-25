package com.apache.fastandroid.demo.kt.sealed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jerry on 2022/1/27.
 */

@Entity(tableName = "user")
data class User(
    @PrimaryKey var id:Int,
    @ColumnInfo(name = "first_name") var name:String,
    var playerType:PlayerViewType = PlayerViewType.BLUE){

}
