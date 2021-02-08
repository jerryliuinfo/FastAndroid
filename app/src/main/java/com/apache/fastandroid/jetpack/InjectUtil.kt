package com.apache.fastandroid.jetpack

import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserModelFactory

/**
 * Created by Jerry on 2021/2/8.
 */
object InjectUtil {

    private fun getUserInfoReporsity() = UserReporsity.get()
    fun getUserModelFactory() = UserModelFactory(getUserInfoReporsity())
}