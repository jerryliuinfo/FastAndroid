package com.apache.fastandroid.demo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apache.fastandroid.demo.bean.Person
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.sealed.User

@Dao
interface PlantDao {

    @Query("SELECT * FROM plant")
     fun queryAll(): List<Plant>

    @Query("SELECT * FROM plant")
     fun queryAllLiveData():LiveData<List<Plant>>

    @Query("SELECT * FROM plant WHERE age > :minAge")
    fun queryByAge(minAge:Long): List<Plant>

    @Query("SELECT * FROM plant WHERE id IN (:ids)")
     fun loadAllByIds(ids: IntArray): List<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plants: List<Plant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(plant: Plant)

    @Delete
    fun delete(plant: Plant)

    @Query("DELETE FROM plant")
    fun deleteAll()



    @Update
     fun update(plant: Plant)

}