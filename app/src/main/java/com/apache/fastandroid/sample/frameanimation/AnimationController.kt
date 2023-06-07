package com.apache.fastandroid.sample.frameanimation

/**
 * Created by Jerry on 2023/5/29.
 */
interface AnimationController {

    fun playAnimationFromAssets(assetsPath: String)

    fun stopAnimation(): Int

}