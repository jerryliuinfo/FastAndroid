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
package com.afollestad.nocknock.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

import com.tesla.framework.component.provider.IntentProvider

typealias SiteCallback = (String) -> Unit

/** @author Aidan Follestad (@afollestad) */
class StatusUpdateIntentReceiver(
  private val context: Context,
  private val intentProvider: IntentProvider,
  private var callback: SiteCallback?
) : LifecycleObserver {

  internal val intentReceiver = object : BroadcastReceiver() {
    override fun onReceive(
      context: Context,
      intent: Intent
    ) {
      if (intent.action == ACTION_STATUS_UPDATE) {
        val result = intent.getStringExtra("result")
            ?: return
        callback?.invoke(result)
      }
    }
  }

  @OnLifecycleEvent(ON_RESUME)
  fun onResume() {
    val filter = intentProvider.createFilter(ACTION_STATUS_UPDATE)
    context.registerReceiver(intentReceiver, filter)
  }

  @OnLifecycleEvent(ON_PAUSE)
  fun onPause() {
    context.unregisterReceiver(intentReceiver)
  }

  @OnLifecycleEvent(ON_DESTROY)
  fun onDestroy() {
    callback = null
  }

  companion object{
    private const val ACTION_STATUS_UPDATE = "STATUS_UPDATE"
  }
}
