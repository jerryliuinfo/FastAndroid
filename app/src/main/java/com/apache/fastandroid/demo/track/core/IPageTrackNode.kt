package com.apache.fastandroid.demo.track.core

import com.pengxr.easytrack.core.ITrackModel

/**
 * Created by pengxr on 10/9/2021
 */
interface IPageTrackNode : ITrackModel {

    fun referrerKeyMap(): Map<String, String>?

    fun referrerSnapshot(): ITrackNode?
}