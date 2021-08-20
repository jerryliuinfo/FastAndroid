package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.BaseAlbumItem
import com.apache.fastandroid.demo.bean.BaseArtistItem
import com.apache.fastandroid.demo.bean.BaseMusicItem
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/8/13.
 */
class GenericClassDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.temp_generic
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}