package com.apache.fastandroid.demo.bean

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/3/20.
 */

@ActivityScoped
class BuiltInActivityQualifer @Inject constructor(@ActivityContext val context: Context) {
}