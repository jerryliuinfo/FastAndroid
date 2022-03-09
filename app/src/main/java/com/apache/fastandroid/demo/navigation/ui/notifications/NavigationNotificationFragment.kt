package com.apache.fastandroid.demo.navigation.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentNotificationsBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

class NavigationNotificationFragment : BaseVBFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

  private  val dashboardViewModel: NotificationsViewModel by viewModels()


  override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    super.layoutInit(inflater, savedInstanceState)
    val textView: TextView = viewBinding.textNotifications
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

  }


}