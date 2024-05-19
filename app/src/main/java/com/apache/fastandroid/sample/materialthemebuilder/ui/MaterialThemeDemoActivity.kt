package com.apache.fastandroid.sample.materialthemebuilder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.apache.fastandroid.R
import com.apache.fastandroid.app.FastApplication
import com.google.android.material.tabs.TabLayout

/**
 * Created by Jerry on 2024/5/19.
 */
class MaterialThemeDemoActivity:AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_theme)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        tabLayout.setupWithViewPager(viewPager)
        val adapter = MainViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter

        (application as FastApplication).mDefaultPreferences
            .nightModeLive.observe(this) { nightMode ->
                nightMode?.let { delegate.localNightMode = it }
            }
    }
}