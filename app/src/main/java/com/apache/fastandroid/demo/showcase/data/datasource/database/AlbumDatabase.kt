package com.apache.fastandroid.demo.showcase.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.example.github.db.GithubDb
import com.apache.fastandroid.demo.showcase.data.datasource.database.AlbumDao
import com.apache.fastandroid.demo.showcase.data.datasource.database.model.AlbumEntityModel
import com.apache.fastandroid.demo.sunflower.utilitis.DATABASE_GITHUB_NAME

@Database(entities = [AlbumEntityModel::class], version = 1, exportSchema = false)
internal abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albums(): AlbumDao

    companion object{

        @Volatile private var instance: AlbumDatabase?= null

        fun getInstance(context: Context): AlbumDatabase {
            return instance ?: synchronized(this){
                instance ?:  buildDatabase(context).also {
                    instance = it
                }
            }

        }
        private fun buildDatabase(context: Context): AlbumDatabase {
            return Room.databaseBuilder(context, AlbumDatabase::class.java, DATABASE_GITHUB_NAME)
                .build()
        }

    }
}
