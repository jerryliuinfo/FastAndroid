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
package com.tesla.framework.kt

import android.os.SystemClock
import android.view.View
import com.tesla.framework.R

object Debouncer {
  @Volatile private var enabled: Boolean = true
  private val enableAgain = Runnable { enabled = true }

  fun canPerform(view: View): Boolean {
    if (enabled) {
      enabled = false
      view.post(enableAgain)
      return true
    }
    return false
  }
}

 fun <T : View> T.onClickDebounced(click: (view: T) -> Unit) {
  setOnClickListener {
    if (Debouncer.canPerform(it)) {
      @Suppress("UNCHECKED_CAST")
      click(it as T)
    }
  }
}




const val SINGLE_CLICK_INTERVAL = 1000

fun View.onSingleClick(
    interval: Int = SINGLE_CLICK_INTERVAL,
    isShareSingleClick: Boolean = true,
    listener: (View) -> Unit
) {
    setOnClickListener {
        determineTriggerSingleClick(interval, isShareSingleClick, listener)
    }

}

fun View.determineTriggerSingleClick(
    interval: Int = SINGLE_CLICK_INTERVAL,
    isShareSingleClick: Boolean = true,
    listener: View.OnClickListener
) {
    val target = if (isShareSingleClick) getActivity(this)?.window?.decorView ?: this else this
    val millis = target.getTag(R.id.single_click_tag_last_single_click_millis) as? Long ?: 0
    if (SystemClock.uptimeMillis() - millis >= interval) {
        target.setTag(R.id.single_click_tag_last_single_click_millis, SystemClock.uptimeMillis())
        listener.onClick(this)
    }
}

