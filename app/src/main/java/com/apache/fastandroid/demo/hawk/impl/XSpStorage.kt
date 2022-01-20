package com.apache.fastandroid.demo.hawk.impl

import android.content.Context
import android.content.SharedPreferences
import com.apache.fastandroid.demo.hawk.interfaces.IStorage

/**
 * Created by Jerry on 2022/1/19.
 */
class XSpStorage: IStorage {

    private lateinit var sharedPreferences: SharedPreferences

    constructor(sharedPreferences: SharedPreferences){
        this.sharedPreferences = sharedPreferences
    }

    constructor(context: Context, spName:String){
        sharedPreferences = context.getSharedPreferences(spName,Context.MODE_PRIVATE)
    }


    override fun <T> put(key: String, value: T):Boolean {
        return editor().putString(key,value.toString()).commit()
    }


    override fun <T> get(key: String): T? {
        return sharedPreferences.getString(key,null) as T
    }


    override fun delete(key: String): Boolean {
        return editor().remove(key).commit()
    }

    override fun deleteAll(): Boolean {
        return editor().clear().commit()
    }

    override fun count(): Int {
        return sharedPreferences.all.size
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    private fun editor():SharedPreferences.Editor{
        return sharedPreferences.edit()
    }
}