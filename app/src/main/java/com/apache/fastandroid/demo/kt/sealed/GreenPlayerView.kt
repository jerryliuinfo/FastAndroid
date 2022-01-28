package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
class GreenPlayerView:PlayerView {
    override fun showView() {
        println("显示绿色button")
    }

    override fun getPlayButton() {
        println("使用绿色播放器播放")
    }
}