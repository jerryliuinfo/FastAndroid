package com.apache.fastandroid.demo.coorinator

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.CoordinatorScrollflagsEnteralwaysCollpaseBinding


import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/11/19.
 * 这个属性和scroll唯一不同的地方是，只要往下拉，AppBarLayout就会显示出来，不必等着Scrollvie到顶部才可以显示
 *
 */
class CoordinatorEnterAlwaysCollpaseFragment :
    BaseBindingFragment<CoordinatorScrollflagsEnteralwaysCollpaseBinding>(CoordinatorScrollflagsEnteralwaysCollpaseBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }


}