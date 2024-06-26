package com.tesla.framework.component.simplestore

import kotlinx.serialization.Serializable

@Serializable
data class Store(
    val key: String? = null,
    val value: String? = null,
    val bool: Boolean? = null
)