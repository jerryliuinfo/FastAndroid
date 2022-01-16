package com.apache.fastandroid.demo

import android.Manifest.permission
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.blankj.utilcode.util.ToastUtils
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/22.
 */
class PermissionXFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_permission
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        PermissionX.init(this).permissions(permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                } else {
                    ToastUtils.showShort("These permissions are denied: %s", deniedList)
                }
            }

    }
}