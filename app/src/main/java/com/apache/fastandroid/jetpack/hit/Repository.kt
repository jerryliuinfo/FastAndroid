package com.apache.fastandroid.jetpack.hit

import javax.inject.Inject

class Repository @Inject constructor() {

    fun doRepositoryWork() {
        println("Do some work in Repository.")
    }

}