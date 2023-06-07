package com.tesla.framework.component.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

/**
 * Created by Jerry on 2023/6/7.
 */
class MyViewModelProvider {

    private var mFactory: Factory? = null
    private var mViewModelStore: MyViewModelStore? = null


    interface Factory {
        /**
         * Creates a new instance of the given `Class`.
         *
         *
         *
         * @param modelClass a `Class` whose instance is requested
         * @param <T>        The type parameter for the ViewModel.
         * @return a newly created ViewModel
        </T> */
        fun <T : MyViewModel> create(modelClass: Class<T>): T
    }


    constructor(owner: MyViewModelStoreOwner) :
            this(
                owner.getViewModelStore(),
                if(owner is MyHasDefaultViewModelProviderFactory) owner.getDefaultViewModelProviderFactory()  else NewInstanceFactory.instance
            )


    /**
     * Creates `ViewModelProvider`, which will create `ViewModels` via the given
     * `Factory` and retain them in a store of the given `ViewModelStoreOwner`.
     *
     * @param owner   a `ViewModelStoreOwner` whose [ViewModelStore] will be used to
     * retain `ViewModels`
     * @param factory a `Factory` which will be used to instantiate
     * new `ViewModels`
     */
    constructor(
        owner: MyViewModelStoreOwner,
        factory: Factory
    ) : this(owner.getViewModelStore(), factory)

    constructor(store: MyViewModelStore, factory: Factory) {
        this.mViewModelStore = store
        this.mFactory = factory
    }


    open class NewInstanceFactory : MyViewModelProvider.Factory {
        override fun <T : MyViewModel> create(modelClass: Class<T>): T {
            return try {
                modelClass.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }

        companion object {
            private var sInstance: NewInstanceFactory? = null

            /**
             * Retrieve a singleton instance of NewInstanceFactory.
             *
             * @return A valid [NewInstanceFactory]
             */
            val instance: NewInstanceFactory
                get() {
                    if (sInstance == null) {
                        sInstance = NewInstanceFactory()
                    }
                    return sInstance!!
                }
        }
    }

}