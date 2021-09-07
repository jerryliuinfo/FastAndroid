package com.apache.fastandroid.demo.room

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment: BaseStatusFragmentNew() {
//    var instance = StudentDatabase.getInstance()
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_room
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

    }
}