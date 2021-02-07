package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.apache.fastandroid.demo.bean.user.UserBean
import com.tesla.framework.common.util.log.NLog
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/1.
 */
class UserInfoViewModel:ViewModel() {

    val user = MutableLiveData<UserBean>()

    val userName:LiveData<String> = Transformations.map(user){
        it.name
    }

    fun loadUser():MutableLiveData<UserBean>{
        if (user.value == null){
            user.postValue(UserBean("Zhangsan",18))
        }
        return user
    }


    fun changeValue(){
        val userBean = UserBean("Python:${Random.nextInt(20)}" ,Random.nextInt(10))
        loadUser().postValue(userBean)
    }

    companion object{
        private const val TAG = "UserInfoViewModel"
    }
}