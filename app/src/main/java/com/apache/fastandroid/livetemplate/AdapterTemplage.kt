package com.apache.fastandroid.livetemplate


sealed class Resource<T>(val data: T?, val message: String?= null) {

    class Loading<T> (data: T? = null) : Resource<T>(data)

    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>( message: String, val cause: Throwable? = null) : Resource<T>(null, message)
}