package com.tesla.framework.kt

import android.app.Activity
import android.app.ActivityManager
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.content.res.use
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.tesla.framework.R
import com.tesla.framework.ui.activity.FragmentArgs
import com.tesla.framework.ui.activity.FragmentContainerActivity
import java.io.File
import kotlin.math.roundToInt
import kotlin.reflect.KProperty1

private const val TABLET_UI_MIN_SCREEN_WIDTH_DP = 720

/**
 * Display a toast in this context.
 *
 * @param resource the text resource.
 * @param duration the duration of the toast. Defaults to short.
 */
fun Context.toast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT, block: (Toast) -> Unit = {}): Toast {
  return toast(getString(resource), duration, block)
}

/**
 * Display a toast in this context.
 *
 * @param text the text to display.
 * @param duration the duration of the toast. Defaults to short.
 */
fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT, block: (Toast) -> Unit = {}): Toast {
  return Toast.makeText(this, text.orEmpty(), duration).also {
    block(it)
    it.show()
  }
}



/**
 * Helper method to create a notification builder.
 *
 * @param id the channel id.
 * @param block the function that will execute inside the builder.
 * @return a notification to be displayed or updated.
 */
fun Context.notificationBuilder(channelId: String, block: (NotificationCompat.Builder.() -> Unit)? = null): NotificationCompat.Builder {
  val builder = NotificationCompat.Builder(this, channelId)
    .setColor(getColor(R.color.accent_blue))
  if (block != null) {
    builder.block()
  }
  return builder
}

/**
 * Helper method to create a notification.
 *
 * @param id the channel id.
 * @param block the function that will execute inside the builder.
 * @return a notification to be displayed or updated.
 */
fun Context.notification(channelId: String, block: (NotificationCompat.Builder.() -> Unit)?): Notification {
  val builder = notificationBuilder(channelId, block)
  return builder.build()
}

/**
 * Checks if the give permission is granted.
 *
 * @param permission the permission to check.
 * @return true if it has permissions.
 */
fun Context.hasPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Returns the color for the given attribute.
 *
 * @param resource the attribute.
 * @param alphaFactor the alpha number [0,1].
 */
@ColorInt
fun Context.getResourceColor(@AttrRes resource: Int, alphaFactor: Float = 1f): Int {
  val typedArray = obtainStyledAttributes(intArrayOf(resource))
  val color = typedArray.getColor(0, 0)
  typedArray.recycle()

  if (alphaFactor < 1f) {
    val alpha = (color.alpha * alphaFactor).roundToInt()
    return Color.argb(alpha, color.red, color.green, color.blue)
  }

  return color
}

@ColorInt
fun Context.getThemeColor(attr: Int): Int {
  val tv = TypedValue()
  return if (this.theme.resolveAttribute(attr, tv, true)) {
    if (tv.resourceId != 0) {
      getColor(tv.resourceId)
    } else {
      tv.data
    }
  } else {
    0
  }
}

/**
 * Converts to dp.
 */
val Int.pxToDp: Int
  get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts to px.
 */
val Int.dpToPx: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts to px and takes into account LTR/RTL layout.
 */
val Float.dpToPxEnd: Float
  get() = (
          this * Resources.getSystem().displayMetrics.density *
                  if (Resources.getSystem().isLTR) 1 else -1
          )

val Resources.isLTR
  get() = configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR

val Context.notificationManager: NotificationManager
  get() = getSystemService()!!

val Context.connectivityManager: ConnectivityManager
  get() = getSystemService()!!

val Context.wifiManager: WifiManager
  get() = getSystemService()!!

val Context.powerManager: PowerManager
  get() = getSystemService()!!

val Context.keyguardManager: KeyguardManager
  get() = getSystemService()!!

val Context.displayCompat: Display?
  get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    display
  } else {
    @Suppress("DEPRECATION")
    getSystemService<WindowManager>()?.defaultDisplay
  }

/** Gets the duration multiplier for general animations on the device
 * @see Settings.Global.ANIMATOR_DURATION_SCALE
 */
val Context.animatorDurationScale: Float
  get() = Settings.Global.getFloat(this.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)

/**
 * Convenience method to acquire a partial wake lock.
 */
fun Context.acquireWakeLock(tag: String): PowerManager.WakeLock {
  val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "$tag:WakeLock")
  wakeLock.acquire()
  return wakeLock
}

/**
 * Function used to send a local broadcast asynchronous
 *
 * @param intent intent that contains broadcast information
 */
fun Context.sendLocalBroadcast(intent: Intent) {
  LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
}

/**
 * Function used to send a local broadcast synchronous
 *
 * @param intent intent that contains broadcast information
 */
fun Context.sendLocalBroadcastSync(intent: Intent) {
  LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent)
}

/**
 * Function used to register local broadcast
 *
 * @param receiver receiver that gets registered.
 */
fun Context.registerLocalReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
  LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
}

/**
 * Function used to unregister local broadcast
 *
 * @param receiver receiver that gets unregistered.
 */
fun Context.unregisterLocalReceiver(receiver: BroadcastReceiver) {
  LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
}

/**
 * Returns true if the given service class is running.
 */
fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
  val className = serviceClass.name
  val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
  @Suppress("DEPRECATION")
  return manager.getRunningServices(Integer.MAX_VALUE)
    .any { className == it.service.className }
}


fun Context.createFileInCacheDir(name: String): File {
  val file = File(externalCacheDir, name)
  if (file.exists()) {
    file.delete()
  }
  file.createNewFile()
  return file
}

/**
 * We consider anything with a width of >= 720dp as a tablet, i.e. with layouts in layout-sw720dp.
 */
fun Context.isTablet(): Boolean {
  return resources.configuration.smallestScreenWidthDp >= TABLET_UI_MIN_SCREEN_WIDTH_DP
}

/**
 * Returns true if current context is in night mode
 */
fun Context.isNightMode(): Boolean {
  return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}


fun Context.isOnline(): Boolean {
  val activeNetwork = connectivityManager.activeNetwork ?: return false
  val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
  val maxTransport = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> NetworkCapabilities.TRANSPORT_LOWPAN
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> NetworkCapabilities.TRANSPORT_WIFI_AWARE
    else -> NetworkCapabilities.TRANSPORT_VPN
  }
  return (NetworkCapabilities.TRANSPORT_CELLULAR..maxTransport).any(networkCapabilities::hasTransport)
}

/**
 * Returns true if device is connected to Wifi.
 */
fun Context.isConnectedToWifi(): Boolean {
  if (!wifiManager.isWifiEnabled) return false

  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
  } else {
    @Suppress("DEPRECATION")
    wifiManager.connectionInfo.bssid != null
  }
}


/**
 * Returns true if [packageName] is installed.
 */
fun Context.isPackageInstalled(packageName: String): Boolean {
  return try {
    packageManager.getApplicationInfo(packageName, 0)
    true
  } catch (e: PackageManager.NameNotFoundException) {
    false
  }
}

fun Context.getApplicationIcon(pkgName: String): Drawable? {
  return try {
    packageManager.getApplicationIcon(pkgName)
  } catch (e: PackageManager.NameNotFoundException) {
    null
  }
}

fun Context.toTheme(@StyleRes styleId: Int): Context {
  return ContextThemeWrapper(this, styleId)
}

fun Context.getAttrColor(@AttrRes attrId: Int): Int {
  return obtainStyledAttributes(intArrayOf(attrId)).use {
    val colorId = it.getResourceId(0, 0)
    ContextCompat.getColor(this, colorId)
  }
}




inline fun <reified T : Activity> Context.startActivity() {
  startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Activity.launchActivity() {
  startActivity(Intent(this, T::class.java))
}


inline fun <reified T : AppCompatActivity> Context.startActivity(
  vararg params: Pair<KProperty1<out Any?, Any?>, Any?>,
) {
  //List 转换成 Array
  val extras = params.map { it.first.name to it.second }.toTypedArray()
  val intent = Intent(this, T::class.java)
  @Suppress("detekt.SpreadOperator")
  intent.putExtras(bundleOf(*extras))
  startActivity(intent)
}

inline fun <reified T: Activity> launchActivity(context: Context) {
  val intent = Intent(context, T::class.java)
  context.startActivity(intent)
}



inline fun <reified T> launchActivity(context: Context, block: Intent.() -> Unit) {
  val intent = Intent(context, T::class.java).apply(block)
  context.startActivity(intent)
}

inline fun <reified T : Fragment> Activity.launchFragment(args: FragmentArgs? = null, addTitleBar:Boolean = true) {
  FragmentContainerActivity.launch(this,T::class.java,args, addTitleBar)
}

internal fun Context.isDebuggable(): Boolean = (0 != (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE))

fun Context.isRtl(ctx: Context): Boolean {
  return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
          ctx.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
}