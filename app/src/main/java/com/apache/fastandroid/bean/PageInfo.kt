package com.apache.fastandroid.bean

/**
 * Created by Jerry on 2022/4/23.
 */
class PageInfo {
    var page = 0

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 0
    }

    fun isFirstPage(): Boolean {
        return page == 0
    }
}