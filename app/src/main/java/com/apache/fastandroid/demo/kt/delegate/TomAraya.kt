package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/5/1.
 */
class TomAraya(n:String):SoundBehavior by ScreamBehavior(n) {
}