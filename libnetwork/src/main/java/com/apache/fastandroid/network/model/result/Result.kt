package com.apache.fastandroid.network.model.result

sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Failure(val throwable: Throwable? = null) : Result<Nothing>


    fun isSuccess():Boolean{


        return this is Success
    }
}
