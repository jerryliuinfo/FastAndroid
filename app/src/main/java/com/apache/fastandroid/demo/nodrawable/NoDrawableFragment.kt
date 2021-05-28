package com.apache.fastandroid.demo.nodrawable

import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.databinding.FragmentNodrawableBinding
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment

/**
 * Created by Jerry on 2021/2/2.
 * android:textCursorDrawable   这个属性是用来控制光标颜色的，@null"   是作用是让光标颜色和text color一样,如果需要自定义颜色，需要自定义一个drawable文件
 */
class NoDrawableFragment: BaseDatebindingFragment<FragmentNodrawableBinding>() {
    /*override fun inflateContentView(): Int {
        return R.layout.fragment_nodrawable
    }*/

    private lateinit var userViewModel: UserViewModel

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_nodrawable,BR.vm,userViewModel)
    }

    override fun initViewModel() {
        userViewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }
}