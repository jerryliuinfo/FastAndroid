package com.tesla.framework.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.tesla.framework.R
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.databinding.CommUiFragmentContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentContainerActivity : BaseVBActivity<CommUiFragmentContainerBinding>(CommUiFragmentContainerBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val className = intent.getStringExtra(EXTRA_CLASS_NAME)
        if (TextUtils.isEmpty(className)) {
            finish()
            return
        }
        Logger.d(String.format("FragmentContainerActivity onCreate this:%s", this))
        val showToolbar = intent.getBooleanExtra(EXTRA_ADD_TITLE_BAR,true)
        val values = intent.getSerializableExtra(EXTRA_ARGS) as FragmentArgs?
        var fragment: Fragment? = null
        if (savedInstanceState == null) {
            try {
                val clazz = Class.forName(className)
                fragment = clazz.newInstance() as Fragment
                // 设置参数给Fragment
                if (values != null && values.values.size > 0) {
                    try {
                        val method = clazz.getMethod(
                            "setArguments", *arrayOf<Class<*>>(
                                Bundle::class.java
                            )
                        )
                        method.invoke(fragment, FragmentArgs.transToBundle(values))
                    } catch (e: Exception) {
                    }
                }
            } catch (e: Exception) {
                finish()
                return
            }
        }
        super.onCreate(savedInstanceState)
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
        }
        showToolbar?.let { showToolbar ->
            if (showToolbar){
                val toolbarView = LayoutInflater.from(this@FragmentContainerActivity).inflate(R.layout.layout_toolbar,null)

                mBinding.header.addView(toolbarView)
            }
            setUpActionBar()
            values?.get("title")?.let { tit ->
                if (tit is String){
                    setToolbarTitle(tit)
                }
            }
        }
    }


    companion object {
        const val FRAGMENT_TAG = "FRAGMENT_CONTAINER"
        const val EXTRA_CLASS_NAME = "className"
        const val EXTRA_ADD_TITLE_BAR = "addTitleBar"
        const val EXTRA_ARGS = "args"

        /**
         * 启动一个界面
         *
         * @param activity
         * @param clazz
         * @param args
         */
        @JvmStatic
        fun launch(activity: Activity, clazz: Class<out Fragment?>, args: FragmentArgs? = null, addTitleBar:Boolean = true) {
            val intent = Intent(activity, FragmentContainerActivity::class.java)
            intent.putExtra(EXTRA_CLASS_NAME, clazz.name)
            intent.putExtra(EXTRA_ADD_TITLE_BAR, addTitleBar)
            args?.let {
                intent.putExtra(EXTRA_ARGS, it)
            }
            activity.startActivity(intent)
        }
        @JvmStatic
        fun launchForResult(
            fragment: Fragment,
            clazz: Class<out Fragment?>,
            args: FragmentArgs?,
            requestCode: Int
        ) {
            launchForResult(fragment.activity as BaseVBActivity<*>?, clazz, args, requestCode)
        }

        fun launchForResult(
            from: BaseVBActivity<*>?,
            clazz: Class<out Fragment?>,
            args: FragmentArgs?,
            requestCode: Int
        ) {
            val intent = Intent(from, FragmentContainerActivity::class.java)
            intent.putExtra(EXTRA_CLASS_NAME, clazz.name)
            if (args != null) intent.putExtra(EXTRA_ARGS, args)
            from!!.startActivityForResult(intent, requestCode)
        }
    }
}