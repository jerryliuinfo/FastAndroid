package com.apache.fastandroid.demo.navigation.ui.home

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
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVMFragment

class NavigationHomeFragment : BaseVMFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

  private  val dashboardViewModel: HomeViewModel by viewModels()


  override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    super.layoutInit(inflater, savedInstanceState)
    Logger.d("NavigationHomeFragment onViewCreated instance: ${hashCode()}")
    val textView: TextView = viewBinding.textHome
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

  }


}