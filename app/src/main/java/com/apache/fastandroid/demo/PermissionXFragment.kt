package com.apache.fastandroid.demo

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentPermissionBinding
import com.blankj.utilcode.util.ToastUtils
import com.permissionx.guolindev.PermissionX
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.lang.Exception

/**
 * Created by Jerry on 2021/10/22.
 */
class PermissionXFragment: BaseVBFragment<FragmentPermissionBinding>(FragmentPermissionBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        viewBinding.btnRequestPermissionNormal.setOnClickListener {
            doNoramlPermissionRequest()
        }
        viewBinding.btnRequestPermissionPermissionX.setOnClickListener {
            PermissionX.init(this).permissions(permission.CAMERA)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                    } else {
                        ToastUtils.showShort("These permissions are denied: %s", deniedList)
                    }
                }
        }


    }

    private fun doNoramlPermissionRequest() {
        if (ContextCompat.checkSelfPermission(requireContext(),permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            call()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission.CALL_PHONE),1)
        }
    }

    private fun call(){
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    ToastUtils.showShort("Your denied CALL_PHONE permission")
                }
            }
        }
    }
}