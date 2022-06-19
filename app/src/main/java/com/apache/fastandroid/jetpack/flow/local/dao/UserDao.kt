package com.apache.fastandroid.jetpack.flow.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apache.fastandroid.jetpack.flow.data.bean.User
import java.util.concurrent.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllByFlow(): kotlinx.coroutines.flow.Flow<List<User>>
    @Insert
    suspend fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

}