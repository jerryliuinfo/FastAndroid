package com.apache.fastandroid.demo.component.activityresult

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentActivityResultBinding
import com.apache.fastandroid.demo.DemoListFragment
import com.tesla.framework.component.activityforresult.ActivityResultHelper
import com.tesla.framework.component.activityresult.ActivityResult
import com.tesla.framework.component.activityresult.coroutine.startActivityAwaitResult
import com.tesla.framework.component.activityresult.launchActivityForResult
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/5/3.
 */
class ActivityResultDemoFragment:BaseBindingFragment<FragmentActivityResultBinding>(FragmentActivityResultBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnActivityHelper.setOnClickListener {
            activityResultHelper()
        }

        mBinding.btnInlineActivityResult.setOnClickListener {
            inlineActivityResult()
        }

        mBinding.btnInlineBundle.setOnClickListener {
            inlineActivityResultWithBundle()
        }

        mBinding.btnInlineActivityResultIntent.setOnClickListener {
            activityResultWithIntent()
        }

        mBinding.btnCoroutine.setOnClickListener {
            activityResultWithCoroutine()
        }
    }

    private fun activityResultWithCoroutine() {
        lifecycleScope.launch {
            val result: ActivityResult = requireActivity().startActivityAwaitResult<SecondActivity>()
            toast("result:${result.success}")
        }
    }

    private fun activityResultWithIntent() {
        val intent = Intent(Intent.ACTION_VIEW)
            .setData("content://some-uri".toUri())
        requireActivity().launchActivityForResult(intent) { success, data ->
            // Do something
            toast("result:${success}")
            showResult(success, msg = data.getStringExtra("input"))
        }
    }

    private fun inlineActivityResultWithBundle() {

        val extras = Bundle()
        extras.putString("some_extra", "Hello, World!")
        requireActivity().launchActivityForResult<SecondActivity>(extras = extras) { success, data ->
            // Do something
            toast("result:${success}")
            showResult(success, msg = data.getStringExtra("input"))
        }

    }

    private fun inlineActivityResult() {
        requireActivity().launchActivityForResult<SecondActivity> { success, data ->
            // Do something
            toast("result:${success}")
            showResult(success, msg = data.getStringExtra("input"))

        }
    }

    private fun activityResultHelper() {
        ActivityResultHelper.newInstance(requireActivity()).startActivityForResult(SecondActivity::class.java
        ) { requestCode, resultCode, data ->
            Logger.d("onActivityResult requestCode:${requestCode}, resultCode:${resultCode}")
            showResult(true, msg = data.getStringExtra("input"))
        }
    }


    private fun showResult(success:Boolean, msg:String?){
        mBinding.tvResult.text = "结果:$success, msg:$msg"
    }
}