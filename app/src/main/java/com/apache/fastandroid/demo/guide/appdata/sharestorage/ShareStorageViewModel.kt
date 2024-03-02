package com.apache.fastandroid.demo.guide.appdata.sharestorage

import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.Utils
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2024/2/3.
 */
class ShareStorageViewModel:ViewModel() {

    private val videoList = mutableListOf<Video>()

    private val contentUri: Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

    private val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE
    )

    // Show only videos that are at least 5 minutes in duration.
    private val selection = "${MediaStore.Video.Media.DURATION} >= ?"
    //5分钟
    private val selectionArgs = arrayOf(
        TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString()
    )

    // Display videos in alphabetical order based on their display name.
    val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

    val query = Utils.getApp().contentResolver.query(
        contentUri,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    fun queryMediaList(){
        viewModelScope.launch() {
            query?.use { cursor ->

                try {
                    // Cache column indices.
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                    val nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                    val durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                    val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
                        val id = cursor.getLong(idColumn)
                        val name = cursor.getString(nameColumn)
                        val duration = cursor.getInt(durationColumn)
                        val size = cursor.getInt(sizeColumn)

                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        // Stores column values and the contentUri in a local object
                        // that represents the media file.
                        videoList += Video(contentUri, name, duration, size)
                    }
                    Logger.d("queryMediaList thread: ${Thread.currentThread().name},videoList:$videoList")
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

    }

    fun loadThumbnail() {
        val thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getAlbumArtAfterQ()
        } else {
            getAlbumArtBeforeQ()
        }
        Logger.d("loadThumbnail thumbnail:$thumbnail")
    }







    private fun getAlbumArtBeforeQ(): Bitmap? {
        val collection = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

        //The columns that you want. We need the ID to build the content uri
        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ART
        )

        //filter by title here
        val selection = "${MediaStore.Audio.Albums._ID} = ?"

        val albumId = getAlbumId()

        //We already know the song title in advance
        val selectionArgs = arrayOf(
            "$albumId"
        )
        val sortOrder = null //sorting order is not needed

        var thumbnail: Bitmap? = null

        Utils.getApp().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val albumArtColIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)

            while (cursor.moveToNext()) {
                val albumArtPath = cursor.getString(albumArtColIndex)

                thumbnail = BitmapFactory.decodeFile(albumArtPath)
                if (thumbnail === null){
                    TODO("Load alternative thumbnail here")
                }
            }
        }

        return thumbnail
    }

    private fun getAlbumId(): Long? {
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        //The columns that you want. We need the ID to build the content uri
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM_ID
        )

        //filter by title here
        val selection = "${MediaStore.Audio.Media.TITLE} = ?"

        //We already know the song title in advance
        val selectionArgs = arrayOf(
            "I Move On (Sintel's Song)"
        )

        val sortOrder = null //sorting order is not needed

        var id: Long? = null

        Utils.getApp().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val albumIdColIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                id = cursor.getLong(albumIdColIndex)
            }
        }

        return id
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getAlbumArtAfterQ(): Bitmap? {
        val collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

        //The columns that you want. We need the ID to build the content uri
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
        )

        //filter by title here
        val selection = "${MediaStore.Audio.Media.TITLE} = ?"

        //We already know the song title in advance
        val selectionArgs = arrayOf(
            "I Move On (Sintel's Song)"
        )
        val sortOrder = null //sorting order is not needed

        var thumbnail: Bitmap? = null

        Utils.getApp().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColIndex)

                //Builds the content uri here
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                try {
                    thumbnail = Utils.getApp().contentResolver.loadThumbnail(
                        uri,
                        Size(300, 300),
                        null
                    )
                } catch (e: IOException) {
                    TODO("Load alternative thumbnail here")
                }
            }
        }
        return thumbnail
    }
}