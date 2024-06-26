package com.apache.fastandroid.demo.temp.activityresult

import android.content.Intent
import android.os.Bundle
import com.tesla.framework.databinding.ActivitySencondBinding
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/3/10.
 */
class SecondActivityDemoActivity:BaseVBActivity<ActivitySencondBinding>(ActivitySencondBinding::inflate) {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        mBinding.btnSetdata.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data", "data from SecondActivity")
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}