package com.tesla.framework.component.cache.image

import android.graphics.Bitmap
import java.lang.ref.SoftReference
import java.util.*

/**
 * Created by Jerry on 2022/6/18.
 */
class MemoryCache {
    private val mCache =
        Collections.synchronizedMap(HashMap<String, SoftReference<Bitmap>>())

    fun put(id:String, bitmap: Bitmap){
        mCache.put(id, SoftReference(bitmap))
    }

    fun get(id:String):Bitmap? {
        return mCache[id]?.get()
    }

    fun clear(){
        mCache.clear()
    }


}