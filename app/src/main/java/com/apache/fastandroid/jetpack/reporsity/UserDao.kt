package com.apache.fastandroid.jetpack.reporsity

/**
 * Created by Jerry on 2021/12/15.
 */
class UserDao {
    companion object{
        private lateinit var sInstance:UserDao
        fun getInstance():UserDao{
            return if (::sInstance.isInitialized) sInstance else UserDao()
        }
    }

    fun getPostCard(address:String):String?{
        return null
    }
}