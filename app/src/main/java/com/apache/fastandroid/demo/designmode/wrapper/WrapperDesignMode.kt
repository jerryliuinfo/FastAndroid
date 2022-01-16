package com.apache.fastandroid.demo.designmode.wrapper

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.Toast
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/13.
 */
class WrapperDesignMode:BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        AContextWrapper(AContext()).doSomething1()

        MyToast.makeText(context,"I am toast",Toast.LENGTH_SHORT).show()
    }


}