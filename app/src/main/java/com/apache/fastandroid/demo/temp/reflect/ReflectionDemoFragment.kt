package com.apache.fastandroid.demo.temp.reflect

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.TempReflectionBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.temp_reflection.*

/**
 * Created by Jerry on 2021/10/27.
 */
class ReflectionDemoFragment:BaseBindingFragment<TempReflectionBinding>(TempReflectionBinding::inflate) {

    private lateinit var drawerLayout:DrawerLayout


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        drawerLayout = findViewById(R.id.drawer)
        drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
        btn_reflect_field.setOnClickListener {
            var drawerListeners = getDrawerListeners()
        }
        btn_reflect_method.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT,true)
//            reflectMethod()
        }
    }


    private fun getDrawerListeners(): List<DrawerListener?>? {
        return try {
            val field = DrawerLayout::class.java.getDeclaredField("mListeners")
            field.isAccessible = true
            // noinspection unchecked
            field[drawerLayout] as List<DrawerListener?>
        } catch (e: Exception) {
            // throw to let developer know the api is changed
            throw RuntimeException(e)
        }
    }

    private fun reflectMethod(){
        val method = DrawerLayout::class.java.getDeclaredMethod("moveDrawerToOffset", View::class.java, Float::class.javaPrimitiveType)
        method.isAccessible = true
        method.invoke(drawerLayout,startDrawer,100f)

    }

    companion object{
        private const val TAG = "ReflectionDemoFragment"
    }

}