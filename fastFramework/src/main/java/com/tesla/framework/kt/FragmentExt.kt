package com.tesla.framework.kt

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.tesla.framework.R
import com.tesla.framework.component.log.Timber

fun Fragment.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    if (canNavigate()) findNavController().navigate(directions, navOptions)
}

/**
 * Returns true if the navigation controller is still pointing at 'this' fragment, or false
 * if it already navigated away.
 */
fun Fragment.canNavigate(): Boolean {
    val navController = findNavController()
    val destinationIdInNavController = navController.currentDestination?.id

    // add unique tag_navigation_destination_id to the res\values\ids.xml:
    val destinationIdOfThisFragment = view?.getTag(R.id.tag_navigation_destination_id) ?: destinationIdInNavController

    // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
    return if (destinationIdInNavController == destinationIdOfThisFragment) {
        view?.setTag(R.id.tag_navigation_destination_id, destinationIdOfThisFragment)
        true
    } else {
        Timber.d("May not navigate: current destination is not the current fragment.")
        false
    }
}





inline fun FragmentActivity.fragmentTransaction(crossinline block: FragmentTransaction.() -> Unit) {
    with(supportFragmentManager.beginTransaction()) {
        block()
        commit()
    }
}
