package com.apache.fastandroid.demo.bean

import android.content.Intent
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

data class MainItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val intent: Intent
)
