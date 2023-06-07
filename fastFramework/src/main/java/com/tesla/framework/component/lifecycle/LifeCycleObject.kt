package com.tesla.framework.component.lifecycle

import com.tesla.framework.component.viewmodel.MyViewModel
import java.io.Closeable

/**
 * Created by Jerry on 2023/6/7.
 */
class LifeCycleObject:Closeable {


    fun cancel(){

    }

    override fun close() {
        cancel()
    }
}


fun LifeCycleObject.life(viewmodel: MyViewModel){
    viewmodel.setTagIfAbsent(toString(), this)
}