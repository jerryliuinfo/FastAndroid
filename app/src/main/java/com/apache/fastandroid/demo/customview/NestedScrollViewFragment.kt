package com.apache.fastandroid.demo.customview

import com.apache.fastandroid.databinding.FragmentNestedScrollviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/2/2.
 * android:textCursorDrawable   这个属性是用来控制光标颜色的，@null"   是作用是让光标颜色和text color一样,如果需要自定义颜色，需要自定义一个drawable文件
 */
class NestedScrollViewFragment: BaseBindingFragment<FragmentNestedScrollviewBinding>(FragmentNestedScrollviewBinding::inflate) {

}