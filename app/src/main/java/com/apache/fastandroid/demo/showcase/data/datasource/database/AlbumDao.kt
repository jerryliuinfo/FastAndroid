package com.apache.fastandroid.demo.showcase.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apache.fastandroid.demo.showcase.data.datasource.database.model.AlbumEntityModel

@Dao
internal interface AlbumDao {

    @Query("SELECT * FROM albums")
    suspend fun getAll(): List<AlbumEntityModel>

    @Query("SELECT * FROM albums where artist = :artistName and name = :albumName and mbId = :mbId")
    suspend fun getAlbum(artistName: String, albumName: String, mbId: String?): AlbumEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntityModel>)
}
