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
package com.tesla.framework.component.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

/** @author Aidan Follestad (@afollestad) */
class DistinctLiveData<T>(source1: LiveData<T>) : MediatorLiveData<T>() {

  private var isInitialized = false
  private var lastValue: T? = null

  init {
    super.addSource(source1) {
      if (!isInitialized) {
        value = it
        isInitialized = true
        lastValue = it
      } else if (lastValue != it) {
        value = it
        lastValue = it
      }
    }
  }

  override fun <S : Any?> addSource(
    source: LiveData<S>,
    onChanged: Observer<in S>
  ) {
    throw UnsupportedOperationException()
  }

  override fun <T : Any?> removeSource(toRemote: LiveData<T>) {
    throw UnsupportedOperationException()
  }
}
