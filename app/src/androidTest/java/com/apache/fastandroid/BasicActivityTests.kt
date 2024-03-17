/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid

import android.util.Log
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import com.apache.fastandroid.demo.guide.bestpractice.test.BasicTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for ActionOpenDocument sample.
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
@LargeTest
class BasicActivityTests {
    val TAG = "BasicActivityTests"

    @Rule
    @JvmField
    @get:Rule var activityScenarioRule = activityScenarioRule<BasicTestActivity>()


    @Before
    fun before() {
       println("before init activityTestRule")
        Log.d(TAG,"before init activityTestRule activityTestRule:$activityScenarioRule")
    }


    @Test
    fun testChangeText(){
        println("testChangeText --->")

        Log.d(TAG,"testChangeText ---->")

    }
}
