package com.tesla.framework.component.viewmodel

/**
 * Created by Jerry on 2023/6/7.
 */
class MyViewModelStore {
    private val mMap = HashMap<String, MyViewModel>()

    fun put(key: String, value: MyViewModel) {
        mMap.put(key, value)?.clear()
    }


    fun get(key: String):MyViewModel?{
        return mMap[key]
    }

    fun keySet():Set<String>{
        return mMap.keys
    }


    fun clear(){
        synchronized(mMap){
            for (item in mMap.values){
                item.clear()
            }
        }
    }

}