package com.apache.fastandroid.jetpack.flow.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(DatabaseBuilder::class) {
            INSTANCE ?: buildRoomDB(context).also {
                INSTANCE = it
            }
        }
    }

    private fun buildRoomDB(context: Context):AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "users"
        ).build()

}