package com.apache.fastandroid.jetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.events.FavoriteEvent
import com.tesla.framework.component.eventbus.flow.EventHub
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/6/25.
 */
class FlowEventViewModel : ViewModel() {


    val mEventHub = EventHub()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    fun sendEvent() {
        viewModelScope.launch {
            mEventHub.dispatch(FavoriteEvent("zhangsan"))
        }
    }


    fun reloadApps() {
        setEffect {
            Effect.ReloadApps()
        }
    }

    fun packageChanged(packageName: String, action: String) {
        setEffect {
            Effect.PackageChanged(packageName, action)
        }
    }

    private fun setEffect(builder: () -> Effect) {
        val newEffect = builder()
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    sealed class Effect {
        data class ReloadApps(val obj: Any? = null) : Effect()

        data class PackageChanged(val packageName: String, val action: String) : Effect()
    }
}