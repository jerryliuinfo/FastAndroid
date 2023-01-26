package com.apache.fastandroid.demo.component.bundle

import android.os.Bundle
import com.apache.fastandroid.bean.Person
import com.apache.fastandroid.databinding.ActivityBundleSourceBinding
import com.skydoves.bundler.intentOf
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/12/21.
 */
class BundleDemoActivity:BaseVBActivity<ActivityBundleSourceBinding>(ActivityBundleSourceBinding::inflate) {

    private var id:Int ?= null
    private var name:String ?= null

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnStartActivity.setOnClickListener {
            intentOf<BundleReceiverActivity> {
                putExtra("id" to 100)
                putExtra("name" to "jerry")
                putExtra("person", Person("jerry",10))
                startActivity(this@BundleDemoActivity)
            }
        }

        if (savedInstanceState != null){
            with(savedInstanceState){
                id = savedInstanceState.getInt(KEY_ID)
                name = savedInstanceState.getString(KEY_NAME)
            }
        }else{
            //初始化默认值
            id = intent.getIntExtra(KEY_ID,-1)
            name = intent.getStringExtra(KEY_NAME)
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            id = savedInstanceState.getInt(KEY_ID)
            name = savedInstanceState.getString(KEY_NAME)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(KEY_ID, 100)
            putString(KEY_NAME, "jerry")
        }
    }



    companion object {
        val KEY_ID = "id"
        val KEY_NAME = "name"
    }
}