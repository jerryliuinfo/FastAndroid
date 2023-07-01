package com.apache.fastandroid.jetpack.hit.qualifier

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/3/20.
 * Hilt 会自动提供一个 Application 的 Context 给 BuiltInApplicationQualifer
 */

@Singleton
class BuiltInApplicationQualifer @Inject constructor(@ApplicationContext val context: Context) {
}