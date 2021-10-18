package com.microsoft.sample.stableids

private var autoId = 0L

data class Item(var subject: String, val summary: String, val id: Long = autoId++)
