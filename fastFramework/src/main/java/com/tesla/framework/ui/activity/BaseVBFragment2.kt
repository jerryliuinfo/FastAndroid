/*
 * Copyright (c) 2020. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.tesla.framework.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.tesla.framework.ui.delegate.FragmentBinding
import com.tesla.framework.ui.delegate.FragmentBindingDelegate
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * @author Dylan Cai
 */
abstract class BaseVBFragment2<VB : ViewBinding> : BaseFragment(),
  FragmentBinding<VB> by FragmentBindingDelegate() {


  override fun inflateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return createViewWithBinding(inflater, container)
  }

}