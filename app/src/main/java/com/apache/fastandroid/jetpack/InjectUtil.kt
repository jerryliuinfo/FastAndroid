package com.apache.fastandroid.jetpack

import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserModelFactory

/**
 * Created by Jerry on 2021/2/8.
 */
object InjectUtil {

     fun getUserInfoReporsity() = UserReporsity.getInstance(UserDao.getInstance(),
        UserNetwork().getInstance()
    )
    fun getUserModelFactory() = UserModelFactory(getUserInfoReporsity())
}