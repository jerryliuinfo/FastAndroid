package com.apache.fastandroid.demo.navigation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.databinding.FragmentDashboardBinding
import com.tesla.framework.ui.fragment.BaseVMFragment

class DashboardFragment : BaseVMFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

  private  val dashboardViewModel: DashboardViewModel by viewModels()


  override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    super.layoutInit(inflater, savedInstanceState)
    val textView: TextView = viewBinding.textDashboard
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

  }


}