package com.apache.fastandroid.demo.location

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.apache.fastandroid.databinding.FragmentLocationPermissionBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/1/29.
 */
class LocationDemoFragment:BaseBindingFragment<FragmentLocationPermissionBinding>(FragmentLocationPermissionBinding::inflate) {

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                ToastUtils.showShort("Precise location access granted")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                ToastUtils.showShort("Only approximate location access granted")

            } else -> {
            // No location access granted.
            ToastUtils.showShort("No location access granted")

        }
        }
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        // Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        requestLocationPermission()

    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val mutableListOf = mutableListOf<String>()

        for (index in 0..4){

        }

        for (index in 0 until 4){

        }
    }
}