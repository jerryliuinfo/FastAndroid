package com.tesla.framework.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonElement





inline fun <reified T:Any> Gson.fromJson(json:JsonElement) = this.fromJson(json,T::class.java)