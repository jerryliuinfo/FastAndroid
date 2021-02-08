package com.apache.fastandroid.jetpack.reporsity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import kotlin.random.Random

/**
 * Created by Jerry on 2021/2/7.
 */
class PostCardReporsity {

    fun getPostCard(address:String):LiveData<String>{
        NLog.d(UserInfoViewModel.TAG, "getPostCard address: ${address}")
        val postCard = MutableLiveData<String>()
        postCard.value = "Jerry: ${Random.nextInt(20)}"
        return postCard
    }


    companion object{
        private lateinit var sInstance: PostCardReporsity

        fun get(): PostCardReporsity {
            sInstance = if (Companion::sInstance.isInitialized) sInstance else PostCardReporsity()
            return sInstance
        }
    }
}