
import android.util.Log
import android.view.View
import com.apache.fastandroid.demo.track.core.*
import com.apache.fastandroid.demo.track.util.trackModel
import com.pengxr.easytrack.core.*
import com.tesla.framework.kt.getActivityFromView

/**
 * Created by pengxr on 2021/8/18.
 */
internal const val TAG = "EasyTrackLib"

class EasyTrack {
    companion object {

        /**
         * Underlying statistical provider.
         */
        internal var providers: MutableList<ITrackProvider> = ArrayList()

        /**
         * Debug or not.
         */
        var debug: Boolean = true

        /**
         * Global Page referrer key map. For example, [cur_page to from_page] means [cur_page] in last page
         * will become [from_page] in current page.
         */
        var referrerKeyMap: Map<String, String>? = null

        /**
         * Register underlying statistical provider.
         */
        fun registerProvider(provider: ITrackProvider) {
            providers.add(provider.apply {
                onInit()
            })
        }

        /**
         * Dispatch event without recursive node tree.
         */
        fun dispatchEvent(event: String, params: TrackParams) {
            for (provider in providers) {
                provider.onEvent(event, params)
            }
        }
    }
}

/**
 * @param eventName Event name.
 * @param otherParams Incoming event params
 *
 * @return Event Params around the node tree.
 */
internal fun Any.doTrackEvent(eventName: String, otherParams: TrackParams? = null): TrackParams? {
    // 1. Check whether the underlying statistical provider are available.
    if (EasyTrack.providers.isEmpty()) {
        Log.d(TAG, "Try track event $eventName, but the providers is Empty.")
        return otherParams
    }
    // 2. Collect data recursively.
    val params = if (this is View || this is TrackModel) {
        fillTrackParams(this, otherParams)
    } else {
        otherParams
    }
    if (null == params) {
        return otherParams
    }
    // 3. Log.
    val logStrBuilder = if (EasyTrack.debug) {
        StringBuilder().apply {
            append(" ")
            append("\nonEvent：$eventName")
            for ((key, value) in params) {
                append("\n$key = $value")
            }
        }
    } else {
        null
    }
    // 4. Do event reporting.
    for (provider in EasyTrack.providers) {
        if (!provider.enabled) {
            logStrBuilder?.append("\nTry track event $eventName, but the provider is disabled.")
            continue
        }
        logStrBuilder?.append("\nTry track event $eventName with provider ${provider.name}.")
        provider.onEvent(eventName, params)
    }
    logStrBuilder?.append("\n------------------------------------------------------")?.also {
        Log.d(TAG, it.toString())
    }
    return params
}

/**
 * Collect data recursively
 */
internal fun fillTrackParams(node: Any?, params: TrackParams? = null): TrackParams {
    val result = params ?: TrackParams()
    var curNode = node
    while (null != curNode) {
        when (curNode) {
            is View -> {
                if (android.R.id.content == curNode.id) {
                    // View Root
                    val activity = getActivityFromView(curNode)
                    curNode = if (activity is IPageTrackNode) {
                        // Activity node.
                        activity.fillTrackParams(result)
                        activity.referrerSnapshot()
                    } else {
                        null
                    }
                } else {
                    // View node
                    curNode.trackModel?.fillTrackParams(result)
                    curNode = curNode.parent
                }
            }
            is ITrackNode -> {
                // Track node
                curNode.fillTrackParams(result)
                curNode = curNode.parent
            }
            else -> {
                curNode = null
            }
        }
    }
    return result
}

///**
// * 创建 Event 实例
// */
//fun View?.newTrackEvent(eventName: String): TrackEvent {
//    this?.doTrackEvent(eventName)
//}

///**
// * 创建 Event 实例
// */
//fun ITrackModel?.newTrackEvent(eventName: String): TrackEvent {
//    this?.doTrackEvent(eventName)
//}