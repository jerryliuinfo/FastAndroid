package com.apache.fastandroid.jetpack.reporsity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Created by Jerry on 2021/2/7.
 */
class UserReporsity( val userDao: UserDao, val userNetwork: UserNetwork) {


    suspend fun getPostCard(address:String) = withContext(Dispatchers.IO) {
        NLog.d(NLog.TAG, "thread222: ${Thread.currentThread().name}")
        var postCard = userDao.getPostCard(address)
        if (postCard == null){
            postCard = userNetwork.getPostCard(address)
        }
        postCard
    }


    companion object{
        private lateinit var sInstance: UserReporsity

        fun getInstance(userDao: UserDao,  userNetwork: UserNetwork): UserReporsity {
            if (!::sInstance.isInitialized){
                synchronized(UserReporsity::class.java){
                    if (!::sInstance.isInitialized){
                        sInstance = UserReporsity(userDao,userNetwork)
                    }
                }
            }
//            sInstance = if (Companion::sInstance.isInitialized) sInstance else UserReporsity()
            return sInstance
        }
    }
}