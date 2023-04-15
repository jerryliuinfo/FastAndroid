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
package com.tesla.framework.component.lifecycle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

import com.tesla.framework.component.provider.IntentProvider

typealias SiteCallback = (String) -> Unit

/** @author Aidan Follestad (@afollestad) */
class LifeCycleIntentReceiver(
  private val context: Context,
  private val intentProvider: IntentProvider,
  private var callback: SiteCallback?
) : DefaultLifecycleObserver {

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

  override fun onResume(owner: LifecycleOwner) {
    super.onResume(owner)
    val filter = intentProvider.createFilter(ACTION_STATUS_UPDATE)
    context.registerReceiver(intentReceiver, filter)
  }

  override fun onPause(owner: LifecycleOwner) {
    super.onPause(owner)
    context.unregisterReceiver(intentReceiver)

  }

  override fun onDestroy(owner: LifecycleOwner) {
    super.onDestroy(owner)
    callback = null
  }



  companion object{
    private const val ACTION_STATUS_UPDATE = "STATUS_UPDATE"
  }
}
