package com.apache.fastandroid.component.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityKeyboardVisibilityBinding
import com.tesla.framework.component.keyboardvisibilityevent.KeyboardVisibilityEvent
import com.tesla.framework.component.keyboardvisibilityevent.Unregistrar
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/9/16.
 * https://github.com/yshrsmz/KeyboardVisibilityEvent
 */
class KeyboardVisibilityDemoFragment:BaseBindingFragment<ActivityKeyboardVisibilityBinding>(ActivityKeyboardVisibilityBinding::inflate) {

    private lateinit var keyboardStatus: TextView
    private lateinit var textField: EditText
    private lateinit var buttonUnregister: Button
    private lateinit var unregistrar: Unregistrar
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        keyboardStatus = findViewById(R.id.keyboard_status)
        textField = findViewById(R.id.text_field)
        buttonUnregister = findViewById(R.id.btn_unregister)

        /*
          You can also use {@link KeyboardVisibilityEvent#setEventListener(Activity, KeyboardVisibilityEventListener)}
          if you don't want to unregister the event manually.
         */

        val activity = requireActivity()
        unregistrar = KeyboardVisibilityEvent.registerEventListener(activity
        ) {isOpen ->
            updateKeyboardStatusText(isOpen)
        }

        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(activity))

        buttonUnregister.setOnClickListener { unregister() }
    }


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