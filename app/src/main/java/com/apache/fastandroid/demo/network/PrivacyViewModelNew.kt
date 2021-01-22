package com.apache.fastandroid.demo.network

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.network.api.AgreementResponseBean
import com.apache.fastandroid.demo.network.reporsity.PrivacyReporsity
import com.tesla.framework.common.util.log.NLog
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2021/1/20.
 */
class PrivacyViewModelNew(private val reporsity: PrivacyReporsity):ViewModel() {
    var privacy = MutableLiveData<AgreementResponseBean>()
    var isLoading = MutableLiveData<Boolean>()


    fun getAgreementData() {
        launch {
            privacy = reporsity.getAgreementData()
            NLog.d(TAG, "getAgreementData privacy: %s", privacy.value)
        }
    }

    companion object{
        private const val TAG = "PrivacyViewModelNew"
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            block()
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            isLoading.value = false
        }
    }
}