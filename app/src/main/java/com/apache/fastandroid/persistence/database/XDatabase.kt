package com.apache.fastandroid.persistence.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apache.fastandroid.demo.kt.sealed.User
import com.blankj.utilcode.util.Utils


@Database(entities = [User::class], version = 1)
abstract class XDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private val db: XDatabase by lazy {
            Room.databaseBuilder(
                Utils.getApp(),
                XDatabase::class.java, "database-name"
            ).build()
        }

        fun userDao(): UserDao {
            return db.userDao()
        }
    }

}