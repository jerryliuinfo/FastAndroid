package com.apache.fastandroid.demo.widget.mergeadapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import masteryi.me.mergeadapterdemo.FooterAdapter

/**
 * @author Ethan Lee
 */
class ContactAdapterViewModel : ViewModel() {
    private var page = 0
    private var loading = false
    val hasMoreState = MutableLiveData<Int>()
    val data = MutableLiveData<List<Int>>()

    fun loadMore() {
        if (loading || hasMoreState.value == FooterAdapter.STATE_COMPLETE) {
            return
        }

        viewModelScope.launch {
            loading = true
            var newData = listOf<Int>()
            var newHasMoreState = FooterAdapter.STATE_LOADING

            // 模拟网络延迟
            withContext(Dispatchers.Default) {
                delay(500)
                newData = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

                if (page >= 1) {
                    newHasMoreState = FooterAdapter.STATE_COMPLETE
                }
            }

            hasMoreState.value = newHasMoreState
            data.value = newData
            page++
            loading = false
        }
    }
}