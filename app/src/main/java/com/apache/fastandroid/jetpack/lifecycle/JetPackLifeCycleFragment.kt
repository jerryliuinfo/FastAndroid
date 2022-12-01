package com.apache.fastandroid.jetpack.lifecycle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleBinding
import com.apache.fastandroid.jetpack.lifecycle.LocationListener.OnLocationChangeListener
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.kt.lifeCycleOwner
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleFragment :
    BaseBindingFragment<FragmentJetpackLifecycleBinding>(FragmentJetpackLifecycleBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        mBinding.tvLocation.setOnClickListener {
            lifecycle.addObserver(LocationListener(this, object : OnLocationChangeListener {
                override fun onLocationChanged(latitude: Int, longtitude: Int) {
                    NLog.d(TAG, "onLocationChanged latitude: %s,longtitude:%s", latitude, longtitude)
                }
            }))
            lifecycle.addObserver(LifeGpsManager.getInstance())
        }

        mBinding.btnDialogDismiss.setOnClickListener {
           dialogDismiss()
        }

    }

    private fun dialogDismiss(){

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("我是标题")
            .setPositiveButton("确认") { _, _ ->
                println("onclick confirm button")
            }
            .show()
        dialog.setOnDismissListener {
            println("dialog onDismiss --->")
        }
        dialog.apply {

            lifeCycleOwner(this@JetPackLifeCycleFragment)
        }
    }


}