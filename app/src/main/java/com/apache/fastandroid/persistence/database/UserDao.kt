package com.apache.fastandroid.persistence.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apache.fastandroid.demo.bean.Person
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.sealed.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

}