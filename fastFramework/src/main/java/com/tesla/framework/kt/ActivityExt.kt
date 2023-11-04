package com.tesla.framework.kt

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.R
import java.io.File

/**
 * Created by Jerry on 2023/11/4.
 */

fun Activity.showMessage(msg: String) {
    this.findViewById<View>(android.R.id.content).showMessage(msg)
}

fun Activity.showSendFileScreen(archiveFilename: String) {
    val file = File(archiveFilename)
    val fileUri = FileProvider.getUriForFile(this, "org.isoron.uhabits", file)
    this.startActivitySafely(
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "application/zip"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
    )
}

fun Activity.startActivitySafely(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        this.showMessage("找不到支持此操作的应用")
    }
}

fun Activity.showSendEmailScreen(@StringRes toId: Int, @StringRes subjectId: Int, content: String?) {
    val to = this.getString(toId)
    val subject = this.getString(subjectId)
    this.startActivity(
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, content)
        }
    )
}

fun Activity.restartWithFade(cls: Class<*>?) {
    Handler().postDelayed(
        {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(Intent(this, cls))
        },
        500
    ) // HACK: Let the menu disappear first
}

fun View.showMessage(msg: String) {
    try {
        val snackbar = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
        val tvId = R.id.snackbar_text
        val tv = snackbar.view.findViewById<TextView>(tvId)
        tv?.setTextColor(Color.WHITE)
        snackbar.show()
    } catch (e: IllegalArgumentException) {
        return
    }
}
