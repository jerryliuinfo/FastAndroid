package com.apache.fastandroid.demo.designmode.facade

/**
 * Created by Jerry on 2023/4/9.
 */
class AudioPlayer:IPlayer {
    override fun play(codec: Codec) {
        println("Playing:${codec.type}")
    }
}