package com.apache.fastandroid.demo.hawk

import android.content.Context
import com.apache.fastandroid.demo.hawk.impl.*
import com.apache.fastandroid.demo.hawk.interfaces.*
import com.google.gson.Gson
import com.orhanobut.logger.Logger

/**
 * Created by Jerry on 2022/1/20.
 */
class XHawkBuilder(private val context: Context) {
    private val STORAGE_TAG_DO_NOT_CHANGE = "Hawk2"

    var storage:IStorage ?= null
        get() {
            if (field == null){
                field = XSpStorage(context,STORAGE_TAG_DO_NOT_CHANGE)
            }

            return field
        }

    var serialize:ISerialize ?= null
        get() {
            if (field == null){
                field = XHawkSerializer(logInterceptor)
            }
            return field
        }

    var conveter:IConverter ?= null
        get(){
            if (field == null){
                field = HawkConverter(parse)
            }
            return field
        }

    var parse:IParser ?= null
        get(){
            if (field == null){
                field = GsonParser(Gson())
            }
            return field
        }

    var encryption:IEncryption ?= null
        get(){
            if (field == null){
                field = NoEncryption()
            }
            return field
        }

    var logInterceptor:ILogInterceptor ?= null
        get(){
            if (field == null){
                field = object :ILogInterceptor{
                    override fun onLog(message: String) {
                        Logger.d(message)
                    }

                }
            }
            return field
        }

    fun build(){
        Hawk.build(this)
    }


}