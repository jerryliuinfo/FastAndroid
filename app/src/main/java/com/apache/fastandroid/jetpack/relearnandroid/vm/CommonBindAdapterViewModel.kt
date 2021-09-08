package com.apache.fastandroid.jetpack.relearnandroid.vm

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2021/9/8.
 */
class CommonBindAdapterViewModel:ViewModel() {
    companion object{
        private const val URL = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2"
    }
    var cover: ObservableField<String> = ObservableField(URL)

    var loading = ObservableBoolean()

    var selected = ObservableBoolean()

    var selected2 = MutableLiveData<Boolean>(false)


    init {
        cover.set(URL)
    }

    fun toggle(){
        selected.set(!selected.get())
    }

    fun toggleLivedata(){
        selected2.value = !(selected2.value!!)
    }



}