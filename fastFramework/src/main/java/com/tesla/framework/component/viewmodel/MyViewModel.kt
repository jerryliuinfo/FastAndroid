package com.tesla.framework.component.viewmodel

import androidx.annotation.MainThread
import java.io.Closeable
import java.io.IOException

/**
 * Created by Jerry on 2023/6/7.
 */
abstract class MyViewModel {

    private var mBagOfTags:MutableMap<String,Any> = mutableMapOf()

    private var mCleared = false


    open fun <T : Any> setTagIfAbsent(key: String, newValue: T): T {
        var previous: T?
        synchronized(mBagOfTags) {
            previous = mBagOfTags[key] as T?
            if (previous == null) {
                mBagOfTags[key] = newValue
            }
        }
        val result = if (previous == null) newValue else previous!!

        if (mCleared){
            closeWithRuntimeException(result)
        }

        return result
    }

    fun <T> getTag(key:String):T?{
        if (mBagOfTags == null){
            return null
        }
        synchronized(mBagOfTags){
            return mBagOfTags.get(key) as T
        }
    }

    @MainThread
    fun clear(){
        mCleared = true
        if (mBagOfTags != null){
            synchronized(mBagOfTags){
                for (value in mBagOfTags.values){
                    closeWithRuntimeException(value)
                }
            }
        }
        onCleared()
    }

    open fun onCleared(){

    }


    private  fun closeWithRuntimeException(obj: Any) {
        if (obj is Closeable) {
            try {
                obj.close()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }
}