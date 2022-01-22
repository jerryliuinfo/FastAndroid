package com.apache.fastandroid.jetpack.relearnandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesla.framework.component.livedata.Event
import com.tesla.framework.component.livedata.SingleLiveEvent
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

    private val _navigationToDetail = MutableLiveData<Boolean>()
    val navigationToDetail: LiveData<Boolean> = _navigationToDetail


    //OK: SingleLiveEvent 方式
    private val _navigationToDetailSingleEvent = SingleLiveEvent<Any>()
        val navigationToDetailSingleEvent:LiveData<Any> = _navigationToDetailSingleEvent

    //推荐 事件包装器方式
    private val _navigationToDetailEventWrapper = MutableLiveData<Event<String>>()
        val navigationToDetailEventWrapper:LiveData<Event<String>> = _navigationToDetailEventWrapper

    fun goToDetail(){
        _navigationToDetail.value = true
    }

    /**
     * fragment 或者 activity监听到数据后就重置一下标志位，但是你这种方法不可靠，万一调用者忘记调用了怎么办
     */
    fun navigationHasHanded(){
        _navigationToDetail.value = false

    }


    fun goToDetailBySingleEvent() {
        _navigationToDetailSingleEvent.call()
    }

    fun goToDetailByEventWrapper(itemId:String) {
        _navigationToDetailSingleEvent.value = Event(itemId)
    }
}