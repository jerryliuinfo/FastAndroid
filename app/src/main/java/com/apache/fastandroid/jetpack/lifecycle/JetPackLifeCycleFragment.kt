package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.lifecycle.LocationListener.OnLocationChangeListener
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleFragment: ABaseFragment() {

    override fun inflateContentView(): Int {
       return R.layout.fragment_jetpack_lifecycle
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        lifecycle.addObserver(LocationListener(this, object : OnLocationChangeListener {
            override fun onLocationChanged(latitude: Int, longtitude: Int) {
                NLog.d(TAG, "onLocationChanged latitude: %s,longtitude:%s",latitude,longtitude)
            }
        }))
    }


}