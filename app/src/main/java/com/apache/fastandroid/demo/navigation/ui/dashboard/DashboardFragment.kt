package com.apache.fastandroid.demo.navigation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentDashboardBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

class DashboardFragment : BaseVBFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

  private  val dashboardViewModel: DashboardViewModel by viewModels()


  override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    super.layoutInit(inflater, savedInstanceState)
    val textView: TextView = viewBinding.textDashboard
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

  }


}