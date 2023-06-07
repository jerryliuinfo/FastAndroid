package com.apache.fastandroid.sample.frameanimation

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * Created by Jerry on 2023/5/29.
 */
class FrameAnimationSurfaceView private constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int):SurfaceView(context, attributeSet, defStyle),AnimationController {
    override fun playAnimationFromAssets(assetsPath: String) {
    }

    override fun stopAnimation(): Int {
        return 0
    }
}