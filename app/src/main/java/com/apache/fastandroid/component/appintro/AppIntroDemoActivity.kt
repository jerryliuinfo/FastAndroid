package com.apache.fastandroid.component.appintro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Created by Jerry on 2023/10/15.
 */
class AppIntroDemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
//        addSlide(
//            AppIntroFragment.createInstance(
//            title = "Welcome...",
//            description = "This is the first slide of the example"
//        ))
//        addSlide(AppIntroFragment.createInstance(
//            title = "...Let's get started!",
//            description = "This is the last slide, I won't annoy you more :)"
//        ))
    }

//    override fun onSkipPressed(currentFragment: Fragment?) {
//        super.onSkipPressed(currentFragment)
//        // Decide what to do when the user clicks on "Skip"
//        finish()
//    }
//
//    override fun onDonePressed(currentFragment: Fragment?) {
//        super.onDonePressed(currentFragment)
//        // Decide what to do when the user clicks on "Done"
//        finish()
//    }
}