package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.*
import com.apache.fastandroid.demo.bean.user.UserBean
import com.apache.fastandroid.jetpack.reporsity.PostCardReporsity
import com.tesla.framework.common.util.log.NLog
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/1.
 */
class UserInfoViewModel(private val reporsity: PostCardReporsity):ViewModel() {


    var count:Int = 0
        get() = field
        set(value) {
            if (value > 100 || value < 0) field = -1
            else field = value
        }


    fun plus(){
        this.count++
    }

    val countLiveData:MutableLiveData<Int> = MutableLiveData(0)

    fun plusNew(){
        countLiveData.value = (countLiveData.value?.plus(1))
    }


    private val addressInput = MutableLiveData<String>()

    fun changeAddress(address:String){
        addressInput.value = address
    }

    val postCard:LiveData<String> = Transformations.switchMap(addressInput) {
        address -> reporsity.getPostCard(address)
    }



    private val user: MutableLiveData<UserBean> by lazy {
        MutableLiveData<UserBean>().also {
//            loadUser()
        }
    }



    val userName:LiveData<String> = Transformations.map(user){ userBean ->
        userBean.name
    }

    fun getUserLiveData():MutableLiveData<UserBean>{
        return user
    }

    private fun loadUser(){
        NLog.d(TAG, "loadUser ---->")
        user.postValue(UserBean("Zhangsan",18))
    }


    fun changeValue(){
        val userBean = UserBean("Python:${Random.nextInt(20)}" ,Random.nextInt(10))
        getUserLiveData().postValue(userBean)
    }



    val livedata1 = MutableLiveData<String>()
    val livedata2 = MutableLiveData<String>()

    val livedataSwitch = MutableLiveData<Boolean>()

    val livedataSwitchMap = Transformations.switchMap(livedataSwitch) {
        if (it){
            return@switchMap livedata1
        }
        return@switchMap livedata2
    }






    companion object{
         const val TAG = "UserInfoViewModel"
    }
}