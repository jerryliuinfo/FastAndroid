package com.apache.fastandroid.demo.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apache.fastandroid.artemis.support.bean.User
import com.tesla.framework.common.util.log.NLog
import kotlin.random.Random

/**
 * Created by Jerry on 2021/1/22.
 */
class UserViewModel:ViewModel() {
     private val users: MutableLiveData<User> = MutableLiveData()

    companion object{
        private const val TAG = "UserViewModel"
    }

    fun getUsers():LiveData<User>{
        return users
    }
    init {
        loadUser()
    }

    private fun loadUser(){
        users.value = User("lisi")
    }

    fun modifyName(){
        users.value= User("wangwu:${Random(10).nextInt()}")
    }

    override fun onCleared() {
        super.onCleared()
        NLog.d(TAG, "UserViewModel onCleared")
    }


    @BindingAdapter("app:goneUnless")
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}