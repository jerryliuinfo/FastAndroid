package com.apache.fastandroid.jetpack.flow.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.jetpack.flow.local.dao.UserDao

/**
 * Created by Jerry on 2022/5/4.
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase:RoomDatabase(){

    abstract fun userDao(): UserDao

}