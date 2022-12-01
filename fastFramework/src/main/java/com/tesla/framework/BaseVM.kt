package com.tesla.framework

import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.Utils
import com.tesla.framework.component.livedata.SingleLiveEvent

/**
 * Created by idisfkj on 2019-08-30.
 * Email : idisfkj@gmail.com.
 */
abstract class BaseVM : AndroidViewModel(Utils.getApp()) {

    val showLoading = SingleLiveEvent<Boolean>()

    abstract fun attach(savedInstanceState: Bundle? = null)

}
