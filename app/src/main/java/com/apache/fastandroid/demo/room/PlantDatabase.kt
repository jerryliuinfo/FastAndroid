package com.apache.fastandroid.demo.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2
import com.blankj.utilcode.util.Utils


@Database(entities = [Plant::class], version = 2,exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao



    companion object {
        val MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
            }

        }
        private val db: PlantDatabase by lazy {
            Room.databaseBuilder(
                Utils.getApp(),
                PlantDatabase::class.java, "users"
            )
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries().build()
        }

        fun getInstance(): PlantDao {
            return db.plantDao()
        }
    }

}