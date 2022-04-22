package com.apache.fastandroid.demo.widget.lyric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.jetpack.BaseViewModel
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2022/4/21.
 */
class LyricViewModel:BaseViewModel() {
    private val _list = MutableLiveData<List<Lrc>>()
    val list:LiveData<List<Lrc>> = _list

    fun loadData(){
        launch(
            {
                withContext(Dispatchers.IO){
                    val time = measureTimeMillis {
                        _list.postValue(LrcHelper.parseLrcFromAssets(Utils.getApp(), "Rolling in the Deep-Adele.lrc"))
                    }
                    println("time cost: $time")
                }
            },{

            }
        )
    }
}