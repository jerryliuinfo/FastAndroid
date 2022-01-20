package com.apache.fastandroid.demo.hawk.impl

import com.apache.fastandroid.demo.hawk.interfaces.IStorage
import com.tencent.mmkv.MMKV

/**
 * Created by Jerry on 2022/1/19.
 */
class XMkkvStorage: IStorage {
    private lateinit var mmkv: MMKV

    constructor(){
        mmkv = MMKV.defaultMMKV()
    }
    constructor( mode:Int, cryptKey:String){
        mmkv =  MMKV.defaultMMKV(mode,cryptKey)
    }

    override fun <T> put(key: String, value: T) :Boolean{
        return mmkv.putString(key,value.toString()).commit()
    }

    override fun <T> get(key: String): T? {
        return mmkv.getString(key,null) as T
    }

    override fun delete(key: String): Boolean {
        return mmkv.remove(key).commit()
    }

    override fun deleteAll(): Boolean {
        return mmkv.clear().commit()
    }

    override fun count(): Int {
        return mmkv.all.size
    }

    override fun contains(key: String): Boolean {
        return mmkv.contains(key)
    }
}