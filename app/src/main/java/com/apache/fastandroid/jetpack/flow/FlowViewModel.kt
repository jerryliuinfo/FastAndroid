package com.apache.fastandroid.jetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.events.FavoriteEvent
import com.tesla.framework.component.eventbus.flow.EventHub
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/6/25.
 */
class FlowViewModel:ViewModel() {


     val mEventHub = EventHub()


    fun sendEvent(){
        viewModelScope.launch {
            mEventHub.dispatch(FavoriteEvent("zhangsan"))


        }
    }
}