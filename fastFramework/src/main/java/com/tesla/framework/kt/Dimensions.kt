/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.tesla.framework.kt

import android.content.res.Resources
import android.util.TypedValue


inline val Int.dp: Int get() = convertDpToPx.toInt()

inline val Float.dp: Float
  get() = toInt().convertDpToPx

inline val Int.convertDpToPx: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics)






inline val Int.sp: Float   get() = convertSpToPx

inline val Int.convertSpToPx: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, toFloat(), Resources.getSystem().displayMetrics)

inline val Float.sp: Float
  get() = toInt().sp
