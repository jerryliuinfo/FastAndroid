package com.apache.fastandroid.demo.temp.savestate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2022/3/14.
 */
class SaveStateViewModel(val savedStateHandle: SavedStateHandle):ViewModel() {
    companion object{
        const val KEY_NAME = "name"
        const val KEY_NUMBER = "num"
    }

    //使用 SaveStateHandle 保存数据
    val nameLiveData = savedStateHandle.getLiveData<String>(KEY_NAME)

    //不使用 SaveStateHandle 保存数据
    val blogLiveData = MutableLiveData<String>()


    private val number: Int = 0

    fun getContentNumber(): MutableLiveData<Int> {
        if (!savedStateHandle.contains(KEY_NUMBER)) {
            savedStateHandle.set(KEY_NUMBER, number)
        }
        return savedStateHandle.getLiveData(KEY_NUMBER)
    }

    fun addNumber() {
        getContentNumber().value = getContentNumber().value?.plus(1)
    }

}