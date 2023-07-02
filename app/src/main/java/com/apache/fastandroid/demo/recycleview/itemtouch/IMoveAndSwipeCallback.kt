package com.apache.fastandroid.demo.recycleview.itemtouch

/**
 * Created by Jerry on 2023/7/2.
 */
/**
 * 拖拽和侧滑抽象接口
 */
interface IMoveAndSwipeCallback {

    fun onMove(prePosition: Int, postPosition: Int)

    fun onSwiped(position: Int)
}