/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.apache.fastandroid.demo.cheese

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityCheeseDetailBinding
import com.apache.fastandroid.databinding.ActivityCheeseMainBinding
import com.apache.fastandroid.demo.cheese.Cheeses.randomCheeseDrawable
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tesla.framework.ui.activity.BaseVmActivityNew

class CheeseDetailActivity : BaseVmActivityNew<ActivityCheeseDetailBinding>(ActivityCheeseDetailBinding::inflate) {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.let { ab ->
            ab.setHomeAsUpIndicator(R.drawable.ic_menu)
            ab.setDisplayHomeAsUpEnabled(true)
        }

        val cheeseName = intent.getStringExtra(EXTRA_NAME)
        mBinding.collapsingToolbar.title = cheeseName

        loadBackdrop()

    }

    private fun loadBackdrop() {
        val imageView: ImageView = findViewById(R.id.backdrop)
        Glide.with(this)
            .load(randomCheeseDrawable)
            .into(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sample_actions, menu)
        return true
    }

    companion object {
        const val EXTRA_NAME = "cheese_name"
    }
}