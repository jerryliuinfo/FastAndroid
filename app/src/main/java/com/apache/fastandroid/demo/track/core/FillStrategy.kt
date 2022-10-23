package com.apache.fastandroid.demo.track.core
import androidx.annotation.IntDef
import com.apache.fastandroid.demo.track.core.FillStrategy.Companion.DEFAULT
import com.apache.fastandroid.demo.track.core.FillStrategy.Companion.NON_CACHE


/**
 * 数据填充策略
 * <p>
 * Created by pengxr on 22/8/2021
 */
@IntDef(DEFAULT, NON_CACHE)
@Retention(AnnotationRetention.SOURCE)
annotation class FillStrategy {
    companion object {
        const val DEFAULT = 0 // 缓存（默认的）
        const val NON_CACHE = 1 // 不缓存
    }
}