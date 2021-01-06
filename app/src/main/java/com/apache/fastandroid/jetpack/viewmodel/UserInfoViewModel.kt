package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2020/11/1.
 */
class UserInfoViewModel:ViewModel() {


    private val user = MutableLiveData<String>()

    fun loadUser():MutableLiveData<String>{
        if (user.value == null){
            user.postValue("Zhangsan")
        }
        return user
    }

}