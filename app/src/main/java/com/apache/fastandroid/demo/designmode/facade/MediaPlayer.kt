package com.apache.fastandroid.demo.designmode.facade

/**
 * Created by Jerry on 2023/4/9.
 * MediaPlayer 提供了三个简单的方法 playAudio()、playVideo() 和 playOgg()，隐藏了复杂的实现细节
 */
class MediaPlayer {
    private val audioPlayer = AudioPlayer()

    private val videoPlayer = VideoPlayer()

    fun playAudio(){
        audioPlayer.play(MPEG4Codec())
    }

    fun playVideo(){
        videoPlayer.play(MPEG4Codec())
    }

    fun playOgg(){
        audioPlayer.play(OggCodec())
    }
}