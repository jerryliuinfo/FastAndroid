package com.apache.fastandroid.demo.designmode.wrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/10/13.
 */
class WrapperDesignMode:BaseVBFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        AContextWrapper(AContext()).doSomething1()

        MyToast.makeText(context,"I am toast",Toast.LENGTH_SHORT).show()
    }


}