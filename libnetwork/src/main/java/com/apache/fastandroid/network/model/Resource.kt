package com.apache.fastandroid.network.model;

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    fun isLoading():Boolean{
        return status == Status.LOADING
    }

    fun isLoadFailed():Boolean{
        return status == Status.ERROR
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?= null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}