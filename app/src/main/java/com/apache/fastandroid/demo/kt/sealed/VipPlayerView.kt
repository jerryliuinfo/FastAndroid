package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */

val TITLE = "播放器默认标题"
val MESSAGE = "播放器默认消息"
class VipPlayerView(var title:String?, var message:String?):PlayerView {
    init {
        title = title ?: TITLE
        message = message ?: MESSAGE
    }

    constructor():this(TITLE, MESSAGE){
        println("无参构造方法")

    }
    constructor( message: String?): this(TITLE, message){
        println("一个参数构造方法")
    }

    override fun showView() {
        println("显示 VIP button")
    }

    override fun getPlayButton() {
        println("使用 VIP 播放器播放")
    }
}