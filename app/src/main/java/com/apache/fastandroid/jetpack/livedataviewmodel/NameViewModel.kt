package com.apache.fastandroid.jetpack.livedataviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2020/11/5.
 */
class NameViewModel:ViewModel() {

    val nameLiveData = MutableLiveData<String>()



}