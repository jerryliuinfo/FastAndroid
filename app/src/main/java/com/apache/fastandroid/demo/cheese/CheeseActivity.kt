package com.apache.fastandroid.demo.cheese

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityCheeseMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.ui.activity.BaseVmActivity

/**
 * Created by Jerry on 2022/4/5.
 */
class CheeseActivity:BaseVmActivity<ActivityCheeseMainBinding>(ActivityCheeseMainBinding::inflate) {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.let { ab ->
            ab.setHomeAsUpIndicator(R.drawable.ic_menu)
            ab.setDisplayHomeAsUpEnabled(true)
        }

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        setupDrawerContent(navigationView)

        setupViewPage()
    }

    private fun setupViewPage() {
        val viewPage = findViewById<ViewPager2>(R.id.viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val adapter = Adapter(this).apply {
            addFragment(CheeseListFragment.newInstance(),"Cateogry1")
            addFragment(CheeseListFragment.newInstance(),"Cateogry2")
            addFragment(CheeseListFragment.newInstance(),"Cateogry3")
        }
        viewPage.adapter = adapter
        TabLayoutMediator(tabLayout,viewPage){ tab,position ->
            tab.text = (adapter.mTitles[position])
        }.attach()
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mBinding.drawerLayout.closeDrawers()
            when(menuItem.itemId){
                R.id.nav_home -> {
                    setToolbarTitle("Home")
                }
                R.id.nav_messages -> {
                    setToolbarTitle("Message")
                }
                R.id.nav_friends -> {
                    setToolbarTitle("Friends")
                }
                R.id.nav_discussion -> {
                    setToolbarTitle("Discussion")
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sample_actions, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                menu.findItem(R.id.menu_night_mode_system).isChecked = true
            }
            AppCompatDelegate.MODE_NIGHT_AUTO -> {
                menu.findItem(R.id.menu_night_mode_auto).isChecked = true
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                menu.findItem(R.id.menu_night_mode_night).isChecked = true
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                menu.findItem(R.id.menu_night_mode_day).isChecked = true
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mBinding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_night_mode_system -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            R.id.menu_night_mode_day -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            R.id.menu_night_mode_night -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            R.id.menu_night_mode_auto -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNightMode(@AppCompatDelegate.NightMode nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }




    internal class Adapter(activity:FragmentActivity): FragmentStateAdapter(activity) {
        private val mFragments:MutableList<Fragment> = ArrayList()
        val mTitles:MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment,title:String){
            mFragments.add(fragment)
            mTitles.add(title)
        }

        override fun getItemCount(): Int {
            return mFragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragments[position]
        }

    }
}