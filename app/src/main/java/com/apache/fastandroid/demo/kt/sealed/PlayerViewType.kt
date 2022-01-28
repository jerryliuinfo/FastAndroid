package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
sealed class PlayerViewType {

    object GREEN:PlayerViewType()

    object BLUE:PlayerViewType()

    class VIP(val title:String?, val message:String?):PlayerViewType()
}

fun getPlayerView(type: PlayerViewType) = when (type) {
    PlayerViewType.GREEN -> GreenPlayerView()
    PlayerViewType.BLUE -> BluePlayerView()
    is PlayerViewType.VIP -> VipPlayerView(type.title, type.message)
}
