package com.apache.fastandroid.network.util

class NetworkException private constructor(val code: Int, message: String) : RuntimeException(message) {

    companion object {
        fun of(code: Int, message: String) = NetworkException(code, message)
    }

}