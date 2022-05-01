package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/5/1.
 */
class ElvisPresley(n:String):SoundBehavior by RockAndRollBehavior(n) {
}