package com.apache.fastandroid.demo.list

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew


/**
 * Description:TODO
 * Create Time:2021/12/19 10:36 下午
 * Author:jerry
 *
 */

class MultiTypeDeomoFragment:BaseStatusFragmentNew() {
 override fun getLayoutId(): Int {
   return R.layout.multitype_demo
 }

 override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
  super.layoutInit(inflater, savedInstanceState)
 }
}