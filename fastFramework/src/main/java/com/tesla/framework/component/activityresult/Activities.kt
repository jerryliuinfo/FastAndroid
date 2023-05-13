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

package com.tesla.framework.component.activityresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tesla.framework.component.activityresult.internal.Scheduler

/**
 * Starts Activity [T] with extras in [extras], and the given [requestCode]. The result
 * is delivered to [onResult].
 */
inline fun <reified T : Activity> FragmentActivity.launchActivityForResult(
  extras: Bundle = Bundle(),
  requestCode: Int = 70,
  noinline onResult: OnResult
) {
  val intent = Intent(this, T::class.java).putExtras(extras)
    launchActivityForResult(
      intent = intent,
      requestCode = requestCode,
      onResult = onResult
  )
}

/**
 * Starts an Activity that [intent] resolves to, with the given [requestCode]. The result
 * is delivered to [onResult].
 */
fun FragmentActivity.launchActivityForResult(
  intent: Intent,
  requestCode: Int = 71,
  onResult: OnResult
) = Scheduler.get().schedule(
    fragmentManager = supportFragmentManager,
    intent = intent,
    requestCode = requestCode,
    onResult = onResult
)
