// (c)2016 Flipboard Inc, All Rights Reserved.
package com.apache.fastandroid.network.model

class FakeThing {
    @JvmField
    var id = 0
    @JvmField
    var name: String? = null
    override fun toString(): String {
        return "FakeThing(id=$id, name=$name)"
    }


}