package com.apache.fastandroid.demo.blockstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.Utils
import com.google.android.gms.auth.blockstore.Blockstore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class State(
    val bytes: String? = null
)

val State.areBytesStored get() = bytes != null

/**
 * ViewModel is used to access the Block Store Data and to observe changes to it.
 */
class BlockStoreViewModel: ViewModel() {
    private val blockStoreRepository: BlockStoreRepository = BlockStoreRepository(Blockstore.getClient(Utils.getApp()))
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = State(bytes = blockStoreRepository.retrieveBytes())
        }
    }

    fun storeBytes(inputString: String) {
        viewModelScope.launch {
            blockStoreRepository.storeBytes(inputString)
            _state.value = State(bytes = blockStoreRepository.retrieveBytes())
        }
    }

     fun clearBytes() {
        viewModelScope.launch {
            blockStoreRepository.clearBytes()
            _state.value = State(bytes = null)
        }
    }
}