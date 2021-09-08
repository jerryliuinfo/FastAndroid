package com.apache.fastandroid.jetpack.relearnandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesla.framework.component.livedata.callback.UnPeekLiveData
import java.util.*

/**
 * Created by Jerry on 2021/9/8.
 */
class ShareViewModel:ViewModel() {

    val name1 = MutableLiveData<String>()

    val name2 = UnPeekLiveData<String>()


    fun toggle(){
        var value = Random(100).nextInt()
        name1.value = "jerry1:$value"
        name2.value = "jerry2:$value"
    }
}