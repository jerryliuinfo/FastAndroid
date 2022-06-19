// SPDX-License-Identifier: GPL-3.0-or-later
package com.apache.fastandroid.demo.designmode.builder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.annotation.DrawableRes
import com.apache.fastandroid.demo.designmode.builder.DialogTitleBuilder
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apache.fastandroid.R

class DialogTitleBuilder(private val context: Context) {
    @StringRes
    private var titleRes = 0

    @StringRes
    private var subtitleRes = 0
    private var subtitle: CharSequence? = null

    @DrawableRes
    private var startIconRes = 0
    private var startIcon: Drawable? = null

    @DrawableRes
    private var endIconRes = 0
    private var endIcon: Drawable? = null
    private var endIconClickListener: View.OnClickListener? = null

    @StringRes
    private var endIconContentDescriptionRes = 0
    private var endIconContentDescription: CharSequence? = null
    private var title: CharSequence? = null
    fun setTitle(title: CharSequence?): DialogTitleBuilder {
        this.title = title
        return this
    }

    fun setTitle(@StringRes titleRes: Int): DialogTitleBuilder {
        this.titleRes = titleRes
        return this
    }

    fun setSubtitle(subtitle: CharSequence?): DialogTitleBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setSubtitle(@StringRes subtitleRes: Int): DialogTitleBuilder {
        this.subtitleRes = subtitleRes
        return this
    }

    fun setStartIcon(startIcon: Drawable?): DialogTitleBuilder {
        this.startIcon = startIcon
        return this
    }

    fun setStartIcon(@DrawableRes startIconRes: Int): DialogTitleBuilder {
        this.startIconRes = startIconRes
        return this
    }

    fun setEndIcon(endIcon: Drawable?, listener: View.OnClickListener?): DialogTitleBuilder {
        this.endIcon = endIcon
        endIconClickListener = listener
        return this
    }

    fun setEndIcon(
        @DrawableRes endIconRes: Int,
        listener: View.OnClickListener?
    ): DialogTitleBuilder {
        this.endIconRes = endIconRes
        endIconClickListener = listener
        return this
    }

    fun setEndIconContentDescription(endIconContentDescription: CharSequence?): DialogTitleBuilder {
        this.endIconContentDescription = endIconContentDescription
        return this
    }

    fun setEndIconContentDescription(@StringRes endIconContentDescriptionRes: Int): DialogTitleBuilder {
        this.endIconContentDescriptionRes = endIconContentDescriptionRes
        return this
    }

    fun build(): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("InflateParams") val v =
            inflater.inflate(R.layout.dialog_title_with_two_icons, null)
        val title = v.findViewById<TextView>(R.id.title)
        val subtitle = v.findViewById<TextView>(R.id.subtitle)
        val startIcon = v.findViewById<ImageView>(R.id.icon)
        val endIcon = v.findViewById<ImageView>(R.id.icon_2)
        // Set title
        if (this.title != null) title.text = this.title else if (titleRes != 0) title.setText(
            titleRes
        )
        // Set subtitle or hide
        if (this.subtitle == null && subtitleRes == 0) subtitle.visibility =
            View.GONE else if (this.subtitle != null) subtitle.text =
            this.subtitle else subtitle.setText(
            subtitleRes
        )
        // Set start icon or hide
        if (this.startIcon == null && startIconRes == 0) startIcon.visibility =
            View.GONE else if (this.startIcon != null) startIcon.setImageDrawable(this.startIcon) else startIcon.setImageResource(
            startIconRes
        )
        // Set end icon or hide
        if (this.endIcon == null && endIconRes == 0) endIcon.visibility =
            View.GONE else if (this.endIcon != null) endIcon.setImageDrawable(this.endIcon) else endIcon.setImageResource(
            endIconRes
        )
        if (endIconClickListener != null) {
            endIcon.setOnClickListener(endIconClickListener)
        }
        if (endIconContentDescription != null) {
            endIcon.contentDescription = endIconContentDescription
        } else if (endIconContentDescriptionRes != 0) {
            endIcon.contentDescription = context.getText(endIconContentDescriptionRes)
        }
        return v
    }
}