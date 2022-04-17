package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingBasisUserBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.ui.fragment.BaseDBFragment
import kotlinx.android.synthetic.main.fragment_databinding_basis_user.*


class DatebinDingBasicDemoFragment: BaseDBFragment<FragmentDatabindingBasisUserBinding>(FragmentDatabindingBasisUserBinding::inflate) {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }
    private lateinit var userViewModel: UserViewModel

    override fun initViewModel() {
        userViewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        val user = UserBean("zhangsan1",18)
        userViewModel.updateUser(user)


        tv_modify_age.setOnClickListener {
//            userViewModel.getDataBindingConfigtUsers().value!!.age = userViewModel.getUsers().value!!.age
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_databinding_basis_user
    }


}