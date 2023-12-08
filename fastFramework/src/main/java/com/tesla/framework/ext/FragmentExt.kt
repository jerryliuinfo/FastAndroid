package com.tesla.framework.ext

import android.app.Activity
import androidx.fragment.app.Fragment
import com.tesla.framework.ui.activity.FragmentContainerActivity

/**
 * Created by Jerry on 2023/12/2.
 */
fun Class<Fragment>.launch(activity: Activity){
    FragmentContainerActivity.launch(activity, this, addTitleBar = false)

}