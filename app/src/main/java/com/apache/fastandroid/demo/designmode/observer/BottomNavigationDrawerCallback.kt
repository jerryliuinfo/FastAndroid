package com.apache.fastandroid.demo.designmode.observer

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Created by Jerry on 2023/7/2.
 */
class BottomNavigationDrawerCallback: BottomSheetBehavior.BottomSheetCallback() {

    private val onStateChangedActions: MutableList<OnStateChangedAction> = mutableListOf()


    override fun onStateChanged(sheet: View, newState: Int) {
        onStateChangedActions.forEach { it.onStateChanged(sheet, newState) }

    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
    }

    fun addOnStateChangedAction(action: OnStateChangedAction): Boolean {
        return onStateChangedActions.add(action)
    }

    fun removeOnStateChangedAction(action: OnStateChangedAction): Boolean {
        return onStateChangedActions.remove(action)
    }
}



