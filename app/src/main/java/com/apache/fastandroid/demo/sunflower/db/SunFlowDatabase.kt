package com.apache.fastandroid.demo.sunflower.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apache.fastandroid.demo.sunflower.bean.Converters
import com.apache.fastandroid.demo.sunflower.bean.GardenPlanting
import com.apache.fastandroid.demo.sunflower.bean.Plant
import com.apache.fastandroid.demo.sunflower.dao.PlantDao

/**
 * Created by Jerry on 2022/3/14.
 */

@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SunFlowDatabase:RoomDatabase() {

    abstract fun plantDao():PlantDao


    companion object{
        const val DATABASE_NAME = "sunflower-db"


        @Volatile private var instance:SunFlowDatabase?= null

        fun getInstance(context: Context):SunFlowDatabase{
            return instance ?: synchronized(this){
                instance ?:  buildDatabase(context).also {
                    instance = it
                }
            }

        }

        private fun buildDatabase(context: Context): SunFlowDatabase {
            return Room.databaseBuilder(context, SunFlowDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}