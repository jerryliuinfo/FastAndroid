package com.apache.fastandroid.demo.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
     fun queryAll(): List<Account>

    @Query("SELECT * FROM account")
     fun queryAllLiveData():LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE age > :minAge")
    fun queryByAge(minAge:Long): List<Account>

    @Query("SELECT * FROM account WHERE id IN (:ids)")
     fun loadAllByIds(ids: IntArray): List<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plants: List<Account>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(plant: Account)

    @Delete
    fun delete(plant: Account)

    @Query("DELETE FROM account")
    fun deleteAll()



    @Update
     fun update(plant: Account)

}