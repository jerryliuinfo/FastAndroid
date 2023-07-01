package com.apache.fastandroid.jetpack.hit.qualifier

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/3/20.
 * 这里 context 是 Activity 类型的，所以不能用 @Singleton 修饰，而是应该用 @ActivityScoped

 */

@ActivityScoped
class BuiltInActivityQualifer @Inject constructor(@ActivityContext val context: Context) {
}