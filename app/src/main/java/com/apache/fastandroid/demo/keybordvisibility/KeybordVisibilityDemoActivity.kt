package com.apache.fastandroid.demo.keybordvisibility

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.apache.fastandroid.databinding.ActivityKeyboardVisibilityEventBinding
import com.tesla.framework.ui.activity.BaseBindingActivity
import com.tesla.framework.component.keyboardvisibilityevent.KeyboardVisibilityEvent
import com.tesla.framework.component.keyboardvisibilityevent.KeyboardVisibilityEventListener
import com.tesla.framework.component.keyboardvisibilityevent.Unregistrar

/**
 * Created by Jerry on 2023/6/20.
 * https://github.com/yshrsmz/KeyboardVisibilityEvent
 */
class KeybordVisibilityDemoActivity:BaseBindingActivity<ActivityKeyboardVisibilityEventBinding>() {

    private lateinit var keyboardStatus: TextView
    private lateinit var textField: EditText
    private lateinit var buttonUnregister: Button
    private lateinit var unregistrar: Unregistrar

    companion object{
        fun getNavigationIntent(
            context: Context,

            ) = Intent(context, KeybordVisibilityDemoActivity::class.java)
    }


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        keyboardStatus = mBinding.keyboardStatus
        textField = mBinding.textField
        buttonUnregister = mBinding.btnUnregister

        unregistrar = KeyboardVisibilityEvent.registerEventListener(this,object :
            KeyboardVisibilityEventListener {
            override fun onVisibilityChanged(isOpen: Boolean) {
                updateKeyboardStatusText(isOpen)
            }

        })
        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(this))
        buttonUnregister.setOnClickListener { unregister() }

    }


    @SuppressLint("SetTextI18n")
    private fun updateKeyboardStatusText(isOpen: Boolean) {
        keyboardStatus.text = "keyboard is ${if (isOpen) "visible" else "hidden"}"
    }



    private fun unregister() {
        unregistrar.unregister()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregistrar.unregister()
    }
}