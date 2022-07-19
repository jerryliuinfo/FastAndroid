package com.apache.fastandroid.demo.kt.lambda

import com.apache.fastandroid.demo.kt.inline.Doggo

/**
 * Created by Jerry on 2022/7/15.
 */

//给 TypeAliasMananger 添加了一个扩展， 参数类型为 Int，返回值为空
typealias TypeAliasCallback = TypeAliasMananger.(Int) -> Unit

typealias Doogges = List<Doggo>

class TypeAliasMananger {

    private var callback:TypeAliasCallback = {}

    fun setCallback(callback: TypeAliasCallback){
        this.callback = callback
    }

    fun trigger(){
        //this 传不传都不影响
        callback(this,10)
    }

    fun train(dogs:Doogges){

    }

}