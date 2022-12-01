package com.tesla.framework

import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.Utils
import com.tesla.framework.support.bean.BaseRecyclerViewModel

/**
 * Created by idisfkj on 2019-09-04.
 * Email : idisfkj@gmail.com.
 */
abstract class BaseRecyclerVM<in T : BaseRecyclerViewModel> :
    AndroidViewModel(Utils.getApp()) {

    abstract fun onBind(model: T?)
}