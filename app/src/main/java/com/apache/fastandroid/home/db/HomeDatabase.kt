package com.apache.fastandroid.home.db

/**
 * Created by Jerry on 2022/2/24.
 */
object HomeDatabase {
    private var homeDao:HomeDao ?= null

    fun getHomeDao():HomeDao{
        if (homeDao == null){
            homeDao = HomeDao()
        }
        return homeDao!!
    }
}