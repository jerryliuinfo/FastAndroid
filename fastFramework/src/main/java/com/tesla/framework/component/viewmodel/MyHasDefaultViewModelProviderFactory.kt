package com.tesla.framework.component.viewmodel

import androidx.lifecycle.ViewModelProvider

/**
 * Created by Jerry on 2023/6/7.
 */
interface MyHasDefaultViewModelProviderFactory {

    fun getDefaultViewModelProviderFactory(): MyViewModelProvider.Factory
}