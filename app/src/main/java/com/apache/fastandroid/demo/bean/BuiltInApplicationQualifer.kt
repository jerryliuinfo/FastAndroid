package com.apache.fastandroid.demo.bean

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/3/20.
 */

@Singleton
class BuiltInApplicationQualifer @Inject constructor(@ApplicationContext val context: Context) {
}