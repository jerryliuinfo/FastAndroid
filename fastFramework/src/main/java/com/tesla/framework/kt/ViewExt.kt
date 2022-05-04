package com.tesla.framework.kt

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.component.livedata.Event
import com.tesla.framework.component.livedata.NetworkLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import kotlin.math.pow

/**
 * dp转换成px
 */
fun Context.dp(dpValue: Float): Float {
    var scale = resources.displayMetrics.density;
    return dpValue * scale + 0.5f
}

fun Context.dpInt(dpValue: Float): Int {
    return dp(dpValue).toInt()
}

val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )


val Int.dp
    get() = this.toFloat().dp

val Int.dpInt
    get() = this.dp.toInt()

val Float.sp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )
val Int.sp
    get() = this.toFloat().sp


fun Float.powWrapp(n: Int): Float = this.pow(n)

val Int.getString
    get()  = Utils.getApp().getString(this)

fun Int.getName(): String {
    return when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}

fun Int.getValue(): String = kotlin.run {
    when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}


fun Int.networkStatus(): String {
    return when(this){
        NetworkLiveData.NetworkState.NONE -> "未连接"
        NetworkLiveData.NetworkState.CONNECT -> "已连接"
        NetworkLiveData.NetworkState.WIFI -> "WIFI"
        NetworkLiveData.NetworkState.CELLULAR -> "移动网络"
        else -> "未知"
    }
}


/**
 * 这个方法但是定义在文件里面的，java中通过 ExtensionsKt.echo() 调用， 定义在文件中的方法最终都会变成public static 的方法和变量
 */
fun echo(){

}

inline fun inlineFunction(msg:String){
    println("inlne:${msg}")
}

fun Array<Int>.swap(pos1:Int, pos2:Int){
    val tmp = this[pos1]
    this[pos1] = this[pos2]
    this[pos2] = tmp


}

fun <T> Array<T>.maxCustomize(greater:(T,T) -> Boolean):T?{
    var max: T? = null
    for (item in this){
        if (max == null || greater(item,max)){
            max = item
        }
    }
    return max
}



@ColorInt
fun @receiver:ColorRes Int.getColor(context: Context): Int = ContextCompat.getColor(context, this)

fun @receiver:DrawableRes Int.getDrawable(context: Context): Drawable? =
    ContextCompat.getDrawable(context, this)

fun @receiver:ColorRes Int.toColorStateList(context: Context): ColorStateList {
    return ColorStateList.valueOf(getColor(context))
}

fun @receiver:ColorInt Int.toColorStateListByColor(): ColorStateList {
    return ColorStateList.valueOf(this)
}

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> LifecycleOwner.inflateBinding(inflater: LayoutInflater): T {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        .filterIsInstance<Class<T>>()
        .first()
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
        .invoke(null, inflater) as T
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query

}

/**
 * Run a function when a View gets measured and laid out on the screen.
 */
fun View.onNextMeasure(runnable: () -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (isLaidOut) {
                viewTreeObserver.removeOnPreDrawListener(this)
                runnable()

            } else if (visibility == View.GONE) {
                Timber.w("View's visibility is set to Gone. It'll never be measured: %s", resourceName())
                viewTreeObserver.removeOnPreDrawListener(this)
            }
            return true
        }
    })
}

fun View.resourceName(): String {
    var name = "<nameless>"
    try {
        name = resources.getResourceEntryName(id)
    } catch (e: Resources.NotFoundException) {
        // Nothing to see here
    }
    return name
}


private fun View.setHeight(height: Int) {
    val params = layoutParams
    params.height = height
    layoutParams = params
}