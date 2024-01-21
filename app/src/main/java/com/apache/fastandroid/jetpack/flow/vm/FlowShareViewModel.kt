package com.apache.fastandroid.jetpack.flow.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/12/10.
 */
class FlowShareViewModel:ViewModel() {

    private val _loginFlow = MutableStateFlow("")

    val loginFlow = _loginFlow.asStateFlow()

    fun startLogin() {
        // Handle login logic here.
        _loginFlow.value = "Login Success"
    }



    private val _loginFlow2 = MutableSharedFlow<String>()

    val loginFlow2 = _loginFlow2.asSharedFlow()

    fun startLogin2() {
        // Handle login logic here.
        viewModelScope.launch {
            _loginFlow2.emit ("Login Success")
        }
    }
}