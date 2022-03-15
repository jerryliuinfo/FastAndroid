package com.apache.fastandroid.demo.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.Utils


@Database(entities = [Account::class], version = 3,exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun plantDao(): AccountDao



    companion object {

        private val db: AccountDatabase by lazy {
            Room.databaseBuilder(
                Utils.getApp(),
                AccountDatabase::class.java, "users"
            )
                .allowMainThreadQueries().build()
        }

        fun getInstance(): AccountDao {
            return db.plantDao()
        }
    }

}