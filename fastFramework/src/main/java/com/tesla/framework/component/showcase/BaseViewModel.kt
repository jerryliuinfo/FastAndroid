package com.tesla.framework.component.showcase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesla.framework.BuildConfig
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

abstract class BaseViewModel<State : BaseState, Action : BaseAction<State>>(initialState: State) :
    ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()


    // Delegate handles state event deduplication (multiple states of the same type holding the same data
    // will not be emitted multiple times to UI)
    private var state by Delegates.observable(initialState) { _, old, new ->
        Logger.d("BaseViewModel state changed old:${old}, new:$new")
        if (old != new) {
            viewModelScope.launch {
                _uiStateFlow.value = new
            }
        }
    }

    protected fun sendAction(action: Action) {
        state = action.reduce(state)
    }
}
