package com.apache.fastandroid.demo.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/1/22.
 */
class UserViewModel:ViewModel() {
    private val users: MutableLiveData<UserBean> = MutableLiveData()
    companion object{
        private const val TAG = "UserViewModel"
    }

    fun getUsers():LiveData<UserBean>{
        return users
    }
    init {
        loadUser()
    }

    private fun loadUser(){
        users.value = UserBean("lisi")
    }

    override fun onCleared() {
        super.onCleared()
        NLog.d(TAG, "UserViewModel onCleared")
    }



    fun refresh(){
        var age  = java.util.Random().nextInt(100)
        NLog.d(TAG, "refresh age: %s", age)
        users.value!!.apply {
            name = "zhangsan:$age"
            NLog.d(TAG, "refresh user: ${users.value}")
        }
//        users.value = UserBean("zhangsan:$age")
    }

    fun updateUser(userBean: UserBean){
        users.value = userBean
    }




}