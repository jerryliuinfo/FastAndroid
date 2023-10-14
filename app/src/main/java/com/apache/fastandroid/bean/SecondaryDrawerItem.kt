package com.apache.fastandroid.bean

/**
 * Created by Jerry on 2023/9/30.
 */
class SecondaryDrawerItem {
    var isSelectable = true
    var nameRes: Int = 0
    var onClick: (() -> Unit)? = null
}