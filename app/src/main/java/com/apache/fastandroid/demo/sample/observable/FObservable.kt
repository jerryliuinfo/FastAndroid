package com.apache.fastandroid.demo.sample.observable

import androidx.databinding.Bindable
import androidx.databinding.Observable

/**
 * Created by Jerry on 2022/4/20.
 */
interface FObservable {

    /**
     * Adds a callback to listen for changes to the Observable.
     * @param callback The callback to start listening.
     */
    fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback?)

    /**
     * Removes a callback from those listening for changes.
     * @param callback The callback that should stop listening.
     */
    fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback?)

    /**
     * The callback that is called by Observable when an observable property has changed.
     */
    abstract class OnPropertyChangedCallback {
        /**
         * Called by an Observable whenever an observable property changes.
         * @param sender The Observable that is changing.
         * @param propertyId The BR identifier of the property that has changed. The getter
         * for this property should be annotated with [Bindable].
         */
        abstract fun onPropertyChanged(sender: Observable?, propertyId: Int)
    }
}