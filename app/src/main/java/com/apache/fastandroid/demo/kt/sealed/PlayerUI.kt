package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
class PlayerUI {

    companion object{
        fun get():PlayerUI{
            return Holder.instance
        }

    }

    private object Holder{
        val instance = PlayerUI()
    }

    fun showPlayer(user: User){
        MediaPlayerView(getPlayerView(user.playerType)).showView()
    }
}