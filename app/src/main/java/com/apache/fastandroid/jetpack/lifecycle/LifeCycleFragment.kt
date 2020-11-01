package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class LifeCycleFragment: Fragment() {
//    override fun inflateContentView(): Int {
//        TODO("Not yet implemented")
//    }

//    override fun inflateContentView(): Int {
//       return R.layout.fragment_jetpack_lifecycle
//    }
//
//    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
//        super.layoutInit(inflater, savedInstanceSate)
//
//        lifecycle.addObserver(LifeCycleListener())
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root =  inflater.inflate(R.layout.fragment_jetpack_lifecycle,container,false)
        lifecycle.addObserver(LifeCycleListener())
        return root
    }


}