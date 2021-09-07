package com.apache.fastandroid.demo.constraint.practice

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2020/11/19.
 * 100 * 100 的TextView 左右居中时可以设置
 * app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintEnd_toEndOf="parent"

 如果是在RelativeLayout中，如果同时设置
android:layout_alignParentLeft="true"
android:layout_alignParentRight="true"，
 则开发者在xml中定义的Textview的宽度不会生效，会被拉伸至屏幕宽度
 */
class ConstraintCenterFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_center
    }
}