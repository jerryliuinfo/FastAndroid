package com.apache.fastandroid.demo.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2021/1/22.
 */
class UserViewModel:ViewModel() {
    private val users: MutableLiveData<UserBean> = MutableLiveData()

    private val _comments = MutableLiveData<List<String>>()
        val commentsLiveData:LiveData<List<String>> = _comments

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
    }



    fun refresh(){
        var age  = java.util.Random().nextInt(100)
        users.value!!.apply {
            name = "zhangsan:$age"
        }
//        users.value = UserBean("zhangsan:$age")
    }

    fun updateUser(userBean: UserBean){
        users.value = userBean
    }

    fun loadCommentData(){
        viewModelScope.launch{
            Logger.d("thread:${Thread.currentThread().name}")
            var commetents = UserReporsity(
                UserDao.getInstance(),
                UserNetwork().getInstance()).loadComment()
            _comments.value = commetents
        }
    }




}