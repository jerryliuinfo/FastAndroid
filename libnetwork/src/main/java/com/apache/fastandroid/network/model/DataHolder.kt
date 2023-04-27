package com.apache.fastandroid.network.model

sealed class DataHolder<out T : Any?> {

    data class Success<out T : Any?>(val data: T?) : DataHolder<T?>()

    data class Error(val error: ErrorHolder) : DataHolder<Nothing>()

    object Loading : DataHolder<Nothing>()
}
