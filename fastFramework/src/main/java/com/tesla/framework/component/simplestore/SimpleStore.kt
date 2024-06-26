@file:Suppress("NewAPI")
package com.tesla.framework.component.simplestore

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher

class SimpleStore(
    private val context: Context?,
    private val name: String?,
    private val encrypted: Boolean?,
    private val dispatcher: CoroutineDispatcher?,
    private val enableCloud: Boolean? = null
) {

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getType(): T {
        return when (T::class.java) {
            SharedPreference::class.java -> sharedPreference() as T
            DatastorePreference::class.java -> datastorePreference() as T
            BlockStore::class.java -> blockStore() as T
            else -> throw Exception("Unhandled return type for preference")
        }
    }

    private fun sharedPreference(): SharedPreference? {
        return context?.let {
            SharedPreference(
                name = name,
                context = it,
                encrypted = encrypted,
                dispatcher = dispatcher
            )
        }
    }

    private fun datastorePreference(): DatastorePreference? {
        return context?.let {
            DatastorePreference(
                name = name,
                context = it,
                encrypted = encrypted
            )
        }
    }

    private fun blockStore(): BlockStore? {
        return context?.let {
            BlockStore(
                context = it,
                enableCloud = enableCloud?: false
            )
        }
    }

    private constructor(builder: Builder) : this(
        builder.context, builder.name, builder.encrypted, builder.dispatcher, builder.enableCloud
    )

    class Builder {
        var context: Context? = null
            private set
        var name: String? = null
            private set
        var encrypted: Boolean? = null
            private set
        var enableCloud: Boolean? = null
            private set
        var dispatcher: CoroutineDispatcher? = null
            private set

        fun context(context: Context) = apply { this.context = context }

        fun storeName(name: String) = apply { this.name = name }

        fun dispatcher(dispatcher: CoroutineDispatcher) = apply { this.dispatcher = dispatcher }

        fun encryption(encrypted: Boolean) = apply { this.encrypted = encrypted }

        fun enableCloudForBlockStore(enableCloud: Boolean) = apply { this.enableCloud = enableCloud }

        fun build() = SimpleStore(this)
    }

}