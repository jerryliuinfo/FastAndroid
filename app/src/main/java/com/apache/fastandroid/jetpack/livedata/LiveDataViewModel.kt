package com.apache.fastandroid.jetpack.livedata

import androidx.lifecycle.*
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.component.logger.Logger
import kotlin.random.Random

/**
 * Created by Jerry on 2023/6/15.
 */
class LiveDataViewModel:ViewModel() {

    private val _mUserInfo: MutableLiveData<UserBean> = MutableLiveData()
     val mUserInfo: LiveData<UserBean> = _mUserInfo

    val mAgeLiveData: LiveData<String> = Transformations.map(_mUserInfo){ userBean ->
        userBean.age.toString()
    }

    val mSwitchMapSourceLiveData = MutableLiveData<Boolean>(false)


    val mediatorLiveSource1 = MutableLiveData<String>()
    val mediatorLiveSource2 = MutableLiveData<Int>()

    val mediatorLiveData = MediatorLiveData<String>().apply {
        addSource(mediatorLiveSource1){
            updateInfo()
        }
        addSource(mediatorLiveSource2){
            updateInfo()
        }
    }

    private fun updateInfo() {
        _mUserInfo.postValue(UserBean(name = mediatorLiveSource1.value, age = mediatorLiveSource2.value ?:100))
    }



    fun changeData(){
        val  name = "Jerry:${Random.nextInt(100)}"
        _mUserInfo.postValue(UserBean(name))
    }


    val livedata1 = MutableLiveData<String>().apply {
        value = "LiveData1 数据"
    }
    val livedata2 = MutableLiveData<String>().apply {
        value = "LiveData2 数据"
    }


    /**
     * 它可以将一个 LiveData 对象转换为另一个 LiveData 对象，并且在源 LiveData 对象的值发生变化时自动更新目标 LiveData 对象。

     */
    val switchMapLivedata:LiveData<String> = Transformations.switchMap(mSwitchMapSourceLiveData) {
        if (it){
            return@switchMap livedata1
        }
        return@switchMap livedata2
    }

    fun doSwitchMap(){
        mSwitchMapSourceLiveData.value =  ! mSwitchMapSourceLiveData.value!!
    }

    private var chagneLivedata1 = true

    fun changeMediaLiveData(){
        if (chagneLivedata1){
            mediatorLiveSource1.value = "zhangsan:${Random.nextInt(10)}"
        }else{
            mediatorLiveSource2.value = Random.nextInt(10)
        }
        chagneLivedata1 = !chagneLivedata1

    }
}