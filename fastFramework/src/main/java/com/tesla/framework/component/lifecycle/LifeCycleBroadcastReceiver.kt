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
class LifeCycleBroadcastReceiver(
    private val context: Context,
    private val intentProvider: IntentProvider,
    private var callback: SiteCallback?,
) : DefaultLifecycleObserver {

    private var isRegistered = false


    internal val intentReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent,
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

    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        unregister(context)

    }

    fun register(context: Context) {
        if (!isRegistered) {
            val filter = intentProvider.createFilter(ACTION_STATUS_UPDATE)
            context.registerReceiver(intentReceiver, filter)
            isRegistered = true
        }
    }

    fun unregister(context: Context) {
        if (isRegistered) {
            context.unregisterReceiver(intentReceiver)
            isRegistered = false
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        callback = null
    }


    companion object {
        private const val ACTION_STATUS_UPDATE = "STATUS_UPDATE"
    }
}
