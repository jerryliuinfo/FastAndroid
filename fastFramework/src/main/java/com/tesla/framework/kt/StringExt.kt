package com.tesla.framework.kt

/**
 * Created by Jerry on 2023/9/27.
 */


fun String.getLocalPartFromEmailAddress(email: String): String? {
    val index = email.lastIndexOf('@')
    return if (index == -1 || index == email.lastIndex) null else email.substring(0, index)
}

fun String.getDomainFromEmailAddress(email: String): String? {
    val index = email.lastIndexOf('@')
    return if (index == -1 || index == email.lastIndex) null else email.substring(index + 1)
}