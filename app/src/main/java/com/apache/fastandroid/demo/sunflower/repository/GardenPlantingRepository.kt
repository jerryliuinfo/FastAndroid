package com.apache.fastandroid.demo.sunflower.repository

import androidx.lifecycle.asLiveData
import com.apache.fastandroid.demo.sunflower.bean.GardenPlanting
import com.apache.fastandroid.demo.sunflower.dao.GardenPlantingDao
import com.apache.fastandroid.demo.sunflower.dao.PlantDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/4/3.
 */
class GardenPlantingRepository(private val gardenPlantingDao: GardenPlantingDao) {

    suspend fun createGradenPlanting(plantId:String){
        val gardenPlanting = GardenPlanting(plantId)
        //ROOM 会默认切线程所以不用显示指定为 io 线程
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting){
        val result = gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
        val gardenPlantingsLiveData = gardenPlantingDao.getPlantedGardens().asLiveData()

        println("removeGardenPlanting plantId:${gardenPlanting.plantId}, result:${result},gardenPlantingsLiveData:${gardenPlantingsLiveData.value}")
    }

    fun isPlanted(plantId: String) = gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    companion object{
        private lateinit var instance:GardenPlantingRepository

        fun getInstance(plantDao: GardenPlantingDao):GardenPlantingRepository{
            return if (::instance.isInitialized) instance else GardenPlantingRepository(plantDao)
        }

    }
}