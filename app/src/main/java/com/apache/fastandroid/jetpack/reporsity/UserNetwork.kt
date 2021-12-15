package com.apache.fastandroid.jetpack.reporsity

/**
 * Created by Jerry on 2021/12/15.
 */
class UserNetwork {

    private var network: UserNetwork? = null

    fun getPostCard(address:String):String?{
        return "Jerry"
    }

    fun getInstance(): UserNetwork {
        if (network == null) {
            synchronized(UserNetwork::class.java) {
                if (network == null) {
                    network = UserNetwork()
                }
            }
        }
        return network!!
    }
}