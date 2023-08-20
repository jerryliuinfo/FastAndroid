package com.apache.fastandroid.network.model


sealed class QueryViewState<out T> {

    companion object {
        fun <T> result(results: List<T>): QueryViewState<T> = Result(results)
        fun <T> idle(): QueryViewState<T> = Idle
        fun <T> loading(): QueryViewState<T> = Loading
        fun <T> error(error: Exception): QueryViewState<T> = Error(error)
    }

    object Idle : QueryViewState<Nothing>()
    object Loading : QueryViewState<Nothing>()
    data class Result<out T>(val _results: List<T>) : QueryViewState<T>()
    data class Error(val _error: Exception) : QueryViewState<Nothing>()

}
