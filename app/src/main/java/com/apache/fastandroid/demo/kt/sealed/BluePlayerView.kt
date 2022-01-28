package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
class BluePlayerView:PlayerView {
    override fun showView() {
        println("显示蓝色button")
    }

    override fun getPlayButton() {
        println("使用蓝色播放器播放")
    }
}