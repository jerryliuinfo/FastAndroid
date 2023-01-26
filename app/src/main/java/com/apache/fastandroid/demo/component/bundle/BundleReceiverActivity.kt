package com.apache.fastandroid.demo.component.bundle

import android.os.Bundle
import com.apache.fastandroid.bean.Person
import com.apache.fastandroid.databinding.ActivityBundleTargetBinding
import com.skydoves.bundler.bundle
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/12/21.
 */
class BundleReceiverActivity:BaseVBActivity<ActivityBundleTargetBinding>(ActivityBundleTargetBinding::inflate) {

    private val id: Long by bundle("id", -1) // initializes a Long extra value lazily.
    private val name: String by bundle("name", "") // initializes a String extra value lazily.
    private val person: Person? by bundle("person") // initializes a Parcelable extra value lazily.

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        mBinding.tvResult.append("id:$id, name:$name, person:$person")
    }
}