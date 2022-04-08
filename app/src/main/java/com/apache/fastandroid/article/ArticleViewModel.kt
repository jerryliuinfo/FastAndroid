package com.apache.fastandroid.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.bean.CollectBean
import com.apache.fastandroid.jetpack.BaseViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2021/10/14.
 */
class ArticleViewModel(private val reporsitory: ArticleReporsitoryKt) : BaseViewModel() {
    @JvmField
    var _status = MutableLiveData<CollectBean>()

    val status:LiveData<CollectBean>
        get() = _status

    fun collect(id: Int) {

        viewModelScope.launch {
//            val result =  reporsitory.collect( id)
            val result =  reporsitory.collect2( id)
            if (result.isSuccess){
                _status.value = CollectBean(id,true)
            }else{
                result.exceptionOrNull()?.let {
                    ToastUtils.showShort(it.message)
                }
            }
        }

    }

    fun unCollect(id: Int) {
        viewModelScope.launch {
            val result =  reporsitory.uncollect( id)
            if (result.isSuccess){
                _status.value = CollectBean(id,true)
            }else{
                result.exceptionOrNull()?.let {
                    ToastUtils.showShort(it.message)
                }

            }
        }
    }
}