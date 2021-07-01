package com.apache.fastandroid.demo.room

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_room.*

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment:BaseFragment() {
//    var instance = StudentDatabase.getInstance()
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_room
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

    }
}