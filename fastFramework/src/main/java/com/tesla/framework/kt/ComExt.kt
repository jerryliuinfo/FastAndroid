package com.tesla.framework.kt

import androidx.databinding.ObservableInt

/**
 * Created by Jerry on 2022/4/20.
 */


fun ObservableInt.increment(){
    set(this.get() + 1)
}