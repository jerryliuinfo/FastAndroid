package com.apache.fastandroid.demo.kt.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


/**
 * Created by Jerry on 2022/1/31.
 */
var map = mutableMapOf<String, Channel<Any>>()

inline fun <reified T> T.post(){
    if (!map.containsKey(T::class.java.name)){
        map.putIfAbsent(T::class.java.name, Channel())
    }
    MainScope().launch {
        launch(AndroidCommonPool){
            map[T::class.java.name]?.send(this@post as Any)
        }
    }
}

inline fun <T, reified R> T.onEvent(noinline action:suspend (R) -> Unit){
    if (!map.containsKey(R::class.java.name)){
        map.put(R::class.java.name,Channel())

    }
    MainScope().launch {
        launch(AndroidCommonPool){
            val receiver = map[R::class.java.name]?.receive()
            launch(Dispatchers.Main){
                action.invoke(receiver as R)
            }
        }
    }


}