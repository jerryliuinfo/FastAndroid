package com.apache.fastandroid.demo.kt.sealed

import java.lang.Exception

/**
 * Created by Jerry on 2022/2/18.
 */
sealed class SealedResult<out T:Any>{

    data class Success<out T:Any>(val data:T): SealedResult<T>()

    data class ERROR(val exception: Exception): SealedResult<Nothing>()

    object InProgress:SealedResult<Nothing>()

}

sealed class SealedResult2<out T:Any>{

    data class Success<out T:Any>(val data:T): SealedResult2<T>()

    sealed class Error(val exception: Exception): SealedResult2<Nothing>(){
        class RecoverableError(exception: Exception):Error(exception)

        class NonRecoverableError(exception: Exception):Error(exception)
    }

    object InProgress:SealedResult2<Nothing>()

}



fun doAction1(result: SealedResult<Int>){
    val action = when(result){
        is SealedResult.ERROR -> println("doAction1 ERROR")
        is SealedResult.InProgress -> println("doAction1 InProgress")
        is SealedResult.Success -> println("doAction1 Success")
    }.exhausitive
}


fun doAction2(result: SealedResult2<Int>){
    val action = when(result){
        is SealedResult2.Success -> println("doAction2 ERROR")
        is SealedResult2.Error.RecoverableError -> println("RecoverableError")
        is SealedResult2.Error.NonRecoverableError -> println("NonRecoverableError")
        is SealedResult2.InProgress -> println("InProgress")
    }
}

val <T> T.exhausitive:T
    get() = this