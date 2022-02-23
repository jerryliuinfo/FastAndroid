package com.apache.fastandroid.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.bean.CollectBean
import com.apache.fastandroid.jetpack.BaseViewModel

/**
 * Created by Jerry on 2021/10/14.
 */
class ArticleViewModel(private val reporsitory: ArticleReporsitoryKt) : BaseViewModel() {
    @JvmField
    var _status = MutableLiveData<CollectBean>()

    val status:LiveData<CollectBean>
        get() = _status

    fun collect(id: Int) {
        launch({
            reporsitory.collect( id)
            _status.value = CollectBean(id,true)
        },{

        })

    }

    fun unCollect(id: Int) {
        launch({
            reporsitory.uncollect( id)
            _status.value = CollectBean(id,false)

        },{

        })
    }
}