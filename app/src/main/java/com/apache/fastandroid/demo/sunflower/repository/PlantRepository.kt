package com.apache.fastandroid.demo.sunflower.repository

import com.apache.fastandroid.demo.sunflower.dao.PlantDao

/**
 * Created by Jerry on 2022/3/14.
 */
class PlantRepository(val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId:String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object{
        private lateinit var instance:PlantRepository

        fun getInstance(plantDao: PlantDao):PlantRepository{
            return if (::instance.isInitialized) instance else PlantRepository(plantDao)
        }

    }
}