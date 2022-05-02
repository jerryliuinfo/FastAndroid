package com.apache.fastandroid.demo.component.once

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.apache.fastandroid.R
import com.apache.fastandroid.component.once.Amount
import com.apache.fastandroid.component.once.Once
import com.apache.fastandroid.databinding.FragmentComponentOnceBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.util.concurrent.TimeUnit

class OnceFragment : BaseVBFragment<FragmentComponentOnceBinding>(FragmentComponentOnceBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        Once.markDone("Application Launched")
        val oncePerSessionButton = findViewById<Button>(R.id.once_per_session_button)
        oncePerSessionButton.setOnClickListener { v: View? ->
            if (!Once.beenDone(Once.THIS_APP_SESSION, SHOW_NEW_SESSION_DIALOG)) {
                showDialog("This dialog should only appear once per app session")
                Once.markDone(SHOW_NEW_SESSION_DIALOG)
            }
        }
        val oncePerInstallButton = findViewById<Button>(R.id.once_per_install_button)
        oncePerInstallButton.setOnClickListener { v: View? ->
            if (!Once.beenDone(Once.THIS_APP_INSTALL, SHOW_FRESH_INSTALL_DIALOG)) {
                showDialog("This dialog should only appear once per app installation")
                Once.markDone(SHOW_FRESH_INSTALL_DIALOG)
            }
        }
        val oncePerVersionButton = findViewById<Button>(R.id.once_per_version_button)
        oncePerVersionButton.setOnClickListener { v: View? ->
            if (!Once.beenDone(Once.THIS_APP_VERSION, SHOW_NEW_VERSION_DIALOG)) {
                showDialog("This dialog should only appear once per app version")
                Once.markDone(SHOW_NEW_VERSION_DIALOG)
            }
        }
        val oncePerMinuteButton = findViewById<Button>(R.id.once_per_minute_button)
        oncePerMinuteButton.setOnClickListener { v: View? ->
            if (!Once.beenDone(TimeUnit.MINUTES, 1, SHOW_MINUTE_DIALOG)) {
                showDialog("This dialog should only appear once per minute")
                Once.markDone(SHOW_MINUTE_DIALOG)
            }
        }
        val oncePerSecondButton = findViewById<Button>(R.id.once_per_second_button)
        oncePerSecondButton.setOnClickListener { v: View? ->
            if (!Once.beenDone(1000L, SHOW_SECOND_DIALOG)) {
                showDialog("This dialog should only appear once per second")
                Once.markDone(SHOW_SECOND_DIALOG)
            }
        }
        val oncePerThreePressesButton = findViewById<Button>(R.id.three_presses_button)
        oncePerThreePressesButton.setOnClickListener { v: View? ->
            val buttonPressedTag = "button pressed"
            Once.markDone(buttonPressedTag)
            if (Once.beenDone(buttonPressedTag, Amount.exactly(3))) {
                showDialog("This dialog should only appear once every three presses")
                Once.clearDone(buttonPressedTag)
            }
        }
        val resetButton = findViewById<Button>(R.id.reset_all_button)
        resetButton.setOnClickListener { v: View? -> Once.clearAll() }

    }


    private fun showDialog(message: String) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        alertDialog.show()
    }

    companion object {
        private const val SHOW_NEW_SESSION_DIALOG = "NewSessionDialog"
        private const val SHOW_FRESH_INSTALL_DIALOG = "FreshInstallDialog"
        private const val SHOW_NEW_VERSION_DIALOG = "NewVersionDialog"
        private const val SHOW_MINUTE_DIALOG = "OncePerMinuteDialog"
        private const val SHOW_SECOND_DIALOG = "OncePerSecondDialog"
    }
}