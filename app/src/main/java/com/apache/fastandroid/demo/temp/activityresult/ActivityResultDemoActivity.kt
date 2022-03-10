package com.apache.fastandroid.demo.temp.activityresult

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.databinding.ActivityResultApiBinding
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2022/3/10.
 * https://mp.weixin.qq.com/s/C62WVau-AU0MH7S4Ix85ew
 */
class ActivityResultDemoActivity:BaseVmActivityNew<ActivityResultApiBinding>(ActivityResultApiBinding::inflate) {


    private val requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            val data = "fromActivityResultApi:"+ result.data?.getStringExtra("data")
            showResult(data)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        if (granted){
            ToastUtils.showShort("用户同意了权限")
        }else{
            ToastUtils.showShort("用户拒绝了权限")
        }
    }

    private val customContract = registerForActivityResult(GetDataFromSecondActivityConstract()){
        it?.let {
            showResult("customContract:$it")
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        it?.let {
            mBinding.ivAvator.setImageBitmap(it)
        }

    }



    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        val intent = Intent(this,SecondActivityDemoActivity::class.java)

        mBinding.btnTraditional.setOnClickListener {
            startActivityForResult(intent,1)
        }

        mBinding.btnResultApi.setOnClickListener {
            requestDataLauncher.launch(intent)
        }

        mBinding.btnRequestPermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        mBinding.btnTakePicture.setOnClickListener {
            takePictureLauncher.launch(null)
        }

        mBinding.btnCustomContract.setOnClickListener {
            customContract.launch(null)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                val data = "traditional:"+intent?.getStringExtra("data")
                showResult(data)
            }
        }
    }

    private fun  showResult(msg:String?){
        mBinding.tvResult.text = msg
    }
}