package com.tesla.framework.kt

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.annotation.StringRes
import androidx.databinding.ObservableInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by Jerry on 2022/4/20.
 */


fun ObservableInt.increment(){
    set(this.get() + 1)
}

infix fun Int.times(str:String) = str.repeat(this)

infix fun String.onto(other:String) = Pair(this,other)


fun <T : Any> Iterable<T>.maxAge(lastModified: (T) -> Long): Long {
    var result = 0L
    forEach {
        result = maxOf(result, lastModified(it))
    }
    return result
}

/**
 * Faster lazy delegation for Android.
 * Warning: Only use for objects accessed on main thread
 */
fun <T> lazyAndroid(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE, initializer)
}



/**
 * InputStream的扩展函数
 */
inline fun InputStream.copyToOut(
    output: OutputStream,
    buffSize: Int = DEFAULT_BUFFER_SIZE, progress: (l: Long) -> Unit
): Long {

    var bytesCopied = 0L
    var buff = ByteArray(buffSize)
    var readBytes = read(buff)
    while (readBytes > 0) {
        output.write(buff, 0, readBytes)
        bytesCopied += readBytes
        readBytes = read(buff)
        progress(bytesCopied);
    }

    return bytesCopied;
}

fun showShortToast(@StringRes resId:Int){
    ToastUtils.showShort(resId)
}

fun showShortToast(msg:String){
    ToastUtils.showShort(msg)
}

fun CoroutineScope.io(codeBlock: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO) {
        codeBlock()
    }
}





internal fun Any.warn(
    message: String,
    vararg args: Any?
) {

}

internal fun Set<String>.equalsStrings(strings: Set<String>): Boolean {
    if (this.size != strings.size) {
        return false
    }
    for ((i, value) in this.withIndex()) {
        if (value != strings.elementAt(i)) {
            return false
        }
    }
    return true
}

internal fun Set<String>.allValues(): Array<String> =
    map { it }.toTypedArray()


inline fun <R> safelyTryCatch( block:() -> R):Result<R>{
    return try {
        Result.success(block())
    }catch (e:Throwable){
        Result.failure(e)
    }
}


fun SharedPreferences.getNonNullString(key: String, defValue: String): String {
    return this.getString(key, defValue) ?: defValue
}


fun Cursor.getColumnValue(columnName:String):String{
    return getString(getColumnIndex(columnName))
}

fun getRootCauseMessage(t: Throwable): String? {
    var rootCause = t
    var nextCause: Throwable?
    do {
        nextCause = rootCause.cause
        if (nextCause != null) {
            rootCause = nextCause
        }
    } while (nextCause != null)
//    if (rootCause is MessagingException) {
//        return rootCause.message
//    }

    // Remove the namespace on the exception so we have a fighting chance of seeing more of the error in the
    // notification.
    val simpleName = rootCause.javaClass.simpleName
    return if (rootCause.localizedMessage != null) simpleName + ": " + rootCause.localizedMessage else simpleName
}


fun Intent.toFullString(): String {
    val sb = StringBuilder()

    sb.append(toString())

    val localExtras = extras ?: return sb.toString()

    sb.append(". Extras{")
    localExtras.keySet().map {
        @Suppress("DEPRECATION")
        it to localExtras.get(it)
    }.joinTo(buffer = sb, separator = ",") { "${it.first}=${it.second}" }
    sb.append('}')

    return sb.toString()
}


fun LifecycleOwner.subscribeOnFirst(lifecycleEvent: Lifecycle.Event, listener: () -> Unit) {
    lateinit var observer: LifecycleObserver
    @Suppress("UnusedPrivateMember")
    observer = LifecycleEventObserver { _, event ->
        if (event == lifecycleEvent) {
            listener()
            lifecycle.removeObserver(observer)
        }
    }

    lifecycle.addObserver(observer)
}