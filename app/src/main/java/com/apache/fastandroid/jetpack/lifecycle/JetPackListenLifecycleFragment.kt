package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackListenLifecycleFragment: BaseVBFragment<FragmentJetpackLifecycleBinding>(FragmentJetpackLifecycleBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        LifeCycleListener().addLifecycleObserver(this)

    }


}