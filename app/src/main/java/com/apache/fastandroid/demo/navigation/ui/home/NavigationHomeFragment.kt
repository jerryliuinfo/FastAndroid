package com.apache.fastandroid.demo.navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.apache.fastandroid.home.HomeViewModelKt
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment

class NavigationHomeFragment : BaseVBFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

  private  val dashboardViewModel: HomeViewModelKt by viewModels()


  override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    super.layoutInit(inflater, savedInstanceState)
    Logger.d("NavigationHomeFragment onViewCreated instance: ${hashCode()}")
    val textView: TextView = viewBinding.textHome
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

  }


}