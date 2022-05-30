package com.apache.fastandroid.demo.mmkv

import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.dylanc.mmkv.*
import com.tencent.mmkv.MMKV

/**
 * Created by Jerry on 2022/5/27.
 */
object MMKVRepository:MMKVOwner {

    override val kv: MMKV
        get() = MMKV.mmkvWithID("default")

     var i1 by mmkvInt()
     var i2 by mmkvInt(default = -1)

     var l1 by mmkvLong()
     var l2 by mmkvLong(default = -1L)

     var b1 by mmkvBool()
     var b2 by mmkvBool(default = true)


     var f1 by mmkvFloat()
     var f2 by mmkvFloat(default = -1f)
     var d1 by mmkvDouble()
     var d2 by mmkvDouble(default = -1.0)
     var s1 by mmkvString()
     var s2 by mmkvString(default = "")
     var set1 by mmkvStringSet()
     var set2 by mmkvStringSet(default = emptySet())
     var bytes1 by mmkvBytes()
     var bytes2 by mmkvBytes(default = byteArrayOf(0x1A))
     var user1 by mmkvParcelable<User>()
     var user2 by mmkvParcelable(default = User(0, "Admin","",""))
}