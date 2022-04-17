package com.tesla.framework.ui.activity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.tesla.framework.R
import com.tesla.framework.databinding.ActivitySingleFragmentBinding

/**
 * Boilerplate for a FragmentActivity containing a single stack of Fragments.
 */
 abstract class SingleFragmentActivity<T : Fragment> : BaseVmActivity<ActivitySingleFragmentBinding>(ActivitySingleFragmentBinding::inflate) {
    lateinit var fragment: T

    override fun initView(rootView: View?) {
        super.initView(rootView)
        val currentFragment: T? = supportFragmentManager.findFragmentById(R.id.fragment_container) as T?
        if (currentFragment != null) {
            fragment = currentFragment
        } else {
            fragment = createFragment()
            supportFragmentManager.commit { add(R.id.fragment_container, fragment) }
        }
    }
    protected abstract fun createFragment(): T

}
