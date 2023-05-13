/**
 * Designed and developed by Aidan Follestad (@afollestad)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package com.tesla.framework.component.activityresult.coroutine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tesla.framework.component.activityresult.ActivityResult
import com.tesla.framework.component.activityresult.launchActivityForResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Starts Activity [T] with extras in [extras], and the given [requestCode]. Returns the result.
 */
suspend inline fun <reified T : Activity> FragmentActivity.startActivityAwaitResult(
  extras: Bundle = Bundle(),
  requestCode: Int = 73
): ActivityResult {
  val intent = Intent(this, T::class.java).putExtras(extras)
  return startActivityAwaitResult(
      intent = intent,
      requestCode = requestCode
  )
}

/**
 * Starts an Activity that [intent] resolves to, with the given [requestCode]. Returns the result.
 */
suspend fun FragmentActivity.startActivityAwaitResult(
  intent: Intent,
  requestCode: Int = 74
): ActivityResult {
  return suspendCoroutine { continuation ->
    launchActivityForResult(
        intent = intent,
        requestCode = requestCode
    ) { success, data ->
      val result = ActivityResult(
          success = success,
          data = data
      )
      continuation.resume(result)
    }
  }
}
