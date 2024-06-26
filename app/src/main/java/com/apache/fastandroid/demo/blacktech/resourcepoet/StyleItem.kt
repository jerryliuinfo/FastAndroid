package com.commit451.resourcespoet

/**
 * An item within a style, like
 *
 * <item name="android:windowBackground">@android:color/black</item>
 */
data class StyleItem(
    val name: String,
    val value: String
)
