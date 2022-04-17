package com.tesla.framework.component

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.tesla.framework.ui.activity.BaseVmActivityNew
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 * Created by jerryliu on 2017/6/6.
 * Fragment是一个神器，是跨Activity和Fragment之前通讯的重要的桥梁
 */
class BizFragment : ABaseFragment() {
    private val realActivity: Activity?
        private get() = if (activity != null) {
            activity
        } else mActivity



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val BIZ_FRAGMENT_TAG = "com.apache.fastandroid.base.BizFragment"
        operator fun get(activity: FragmentActivity): BizFragment? {
            return activity.supportFragmentManager.findFragmentByTag(BIZ_FRAGMENT_TAG) as BizFragment?
        }

        fun createBizFragment(fragment: BaseVBFragment<*>?): BizFragment? {
            if (fragment != null && fragment.activity != null) {
                var bizFragment = fragment.activity!!.supportFragmentManager.findFragmentByTag(
                    BIZ_FRAGMENT_TAG
                ) as BizFragment?
                if (bizFragment == null) {
                    bizFragment = BizFragment()
                    fragment.activity!!
                        .supportFragmentManager.beginTransaction()
                        .add(bizFragment, BIZ_FRAGMENT_TAG).commitAllowingStateLoss()
                }
                return bizFragment
            }
            return null
        }

        fun createBizFragment(activity: AppCompatActivity): BizFragment {
            var bizFragment =
                activity.supportFragmentManager.findFragmentByTag(BIZ_FRAGMENT_TAG) as BizFragment?
            if (bizFragment == null) {
                bizFragment = BizFragment()
                bizFragment.mActivity = activity
                if (activity is BaseVmActivityNew<*> && activity.isDestroyed) {
                    return bizFragment
                }
                activity.supportFragmentManager.beginTransaction()
                    .add(bizFragment, BIZ_FRAGMENT_TAG).commit()
            }
            return bizFragment
        }
    }
}