/*
 * Copyright (C) 2016-2021 Álinson Santos Xavier <git@axavier.org>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.apache.fastandroid.demo.drakeet.customview

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM
import android.widget.RelativeLayout.ALIGN_PARENT_TOP
import android.widget.RelativeLayout.BELOW
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.PaletteColor
import com.apache.fastandroid.ui.views.Theme
import com.apache.fastandroid.ui.views.toInt
import com.apache.fastandroid.util.ColorUtils
import com.apache.fastandroid.util.InterfaceUtils
import com.apache.fastandroid.util.StyledResources
import com.google.android.material.snackbar.Snackbar

import java.io.File
import kotlin.math.roundToInt

fun RelativeLayout.addBelow(
    view: View,
    subject: View,
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    applyCustomRules: (params: RelativeLayout.LayoutParams) -> Unit = {}
) {
    view.layoutParams = RelativeLayout.LayoutParams(width, height).apply {
        addRule(BELOW, subject.id)
        applyCustomRules(this)
    }
    view.id = View.generateViewId()
    this.addView(view)
}

fun RelativeLayout.addAtBottom(
    view: View,
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT
) {
    view.layoutParams = RelativeLayout.LayoutParams(width, height).apply {
        addRule(ALIGN_PARENT_BOTTOM)
    }
    view.id = View.generateViewId()
    this.addView(view)
}

fun RelativeLayout.addAtTop(
    view: View,
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT
) {
    view.layoutParams = RelativeLayout.LayoutParams(width, height).apply {
        addRule(ALIGN_PARENT_TOP)
    }
    view.id = View.generateViewId()
    this.addView(view)
}

fun ViewGroup.buildToolbar(): Toolbar {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(R.layout.toolbar, null) as Toolbar
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

fun View.setupToolbar(
    toolbar: Toolbar,
    title: String,
    color: PaletteColor,
    theme: Theme,
    displayHomeAsUpEnabled: Boolean = true
) {
    toolbar.elevation = InterfaceUtils.dpToPixels(context, 2f)
    val res = StyledResources(context)
    toolbar.title = title
    val toolbarColor = if (!res.getBoolean(R.attr.useHabitColorAsPrimary)) {
        StyledResources(context).getColor(R.attr.colorPrimary)
    } else {
        theme.color(color).toInt()
    }
    val darkerColor = ColorUtils.mixColors(toolbarColor, Color.BLACK, 0.75f)
    toolbar.background = ColorDrawable(toolbarColor)
    val activity = context as AppCompatActivity
    activity.window.statusBarColor = darkerColor
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
}

fun Int.toMeasureSpec(mode: Int) =
    View.MeasureSpec.makeMeasureSpec(this, mode)

fun Float.toMeasureSpec(mode: Int) =
    View.MeasureSpec.makeMeasureSpec(toInt(), mode)

fun View.isRTL() = InterfaceUtils.isLayoutRtl(this)
fun View.getFontAwesome() = InterfaceUtils.getFontAwesome(context)!!
fun View.dim(id: Int) = InterfaceUtils.getDimension(context, id)
fun View.sp(value: Float) = InterfaceUtils.spToPixels(context, value)
fun View.dp(value: Float) = InterfaceUtils.dpToPixels(context, value)
fun View.str(id: Int) = resources.getString(id)

fun View.drawNotesIndicator(canvas: Canvas, color: Int, size: Float, notes: String) {
    val pNotesIndicator = Paint()
    pNotesIndicator.color = color
    if (notes.isNotBlank()) {
        val cy = 0.8f * size
        canvas.drawCircle(width.toFloat() - cy, cy, 8f, pNotesIndicator)
    }
}

val View.sres: StyledResources
    get() = StyledResources(context)

fun Dialog.dimBehind() {
    window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    window?.setDimAmount(0.5f)
}

fun View.requestFocusWithKeyboard() {
    // For some reason, Android does not open the soft keyboard by default when view.requestFocus
    // is called. Several online solutions suggest using InputMethodManager, but these solutions
    // are not reliable; sometimes the keyboard does not show, and sometimes it does not go away
    // after focus is lost. Here, we simulate a click on the view, which triggers the keyboard.
    // Based on: https://stackoverflow.com/a/7699556
    postDelayed({
        val time = SystemClock.uptimeMillis()
        dispatchTouchEvent(MotionEvent.obtain(time, time, MotionEvent.ACTION_DOWN, 0f, 0f, 0))
        dispatchTouchEvent(MotionEvent.obtain(time, time, MotionEvent.ACTION_UP, 0f, 0f, 0))
    }, 250)
}

