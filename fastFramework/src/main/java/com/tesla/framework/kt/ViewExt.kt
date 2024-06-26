package com.tesla.framework.kt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.component.livedata.Event
import com.tesla.framework.component.livedata.NetworkLiveData
import com.tesla.framework.component.log.Timber
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


/**
 * 监听 EditText 改变
 * @receiver EditText
 * @param debounce Int
 * @param cb Function1<String, Unit>
 */

fun EditText.onTextChanged(
    @IntRange(from = 0, to = 10000) debounce: Int = 0,
    cb: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        val callbackRunner = Runnable {
            cb(text.trim().toString())
        }

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            removeCallbacks(callbackRunner)
            if (debounce == 0) {
                callbackRunner.run()
            } else {
                postDelayed(callbackRunner, debounce.toLong())
            }
        }
    })
}


@Suppress("DEPRECATION")
fun TextView.setTextAppearanceCompat(context: Context, resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        setTextAppearance(resId)
    } else {
        setTextAppearance(context, resId)
    }
}

fun  Int.networkStatus(): String {
    return when (this) {
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
fun echo() {

}

inline fun inlineFunction(msg: String) {
    println("inlne:${msg}")
}

fun Array<Int>.swap(pos1: Int, pos2: Int) {
    val tmp = this[pos1]
    this[pos1] = this[pos2]
    this[pos2] = tmp


}

fun <T> Array<T>.maxCustomize(greater: (T, T) -> Boolean): T? {
    var max: T? = null
    for (item in this) {
        if (max == null || greater(item, max)) {
            max = item
        }
    }
    return max
}


//@Suppress("UNCHECKED_CAST")
//fun <T : ViewBinding> LifecycleOwner.inflateBinding(inflater: LayoutInflater): T {
//    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
//        .filterIsInstance<Class<T>>()
//        .first()
//        .getDeclaredMethod("inflate", LayoutInflater::class.java)
//        .invoke(null, inflater) as T
//}

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
                Timber.w(
                    "View's visibility is set to Gone. It'll never be measured: %s",
                    resourceName()
                )
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


fun RecyclerView.smoothScrollToPositionWithSpeed(
    position: Int,
    millisPerInch: Float = 100f
) {
    val lm = requireNotNull(layoutManager)
    val scroller = object : LinearSmoothScroller(context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return millisPerInch / displayMetrics.densityDpi
        }
    }
    scroller.targetPosition = position
    lm.startSmoothScroll(scroller)
}

fun Activity.showToast(message: CharSequence) {
    ToastUtils.showShort(message)
}

fun Fragment.showToast(message: CharSequence) {
    ToastUtils.showShort(message)
}


typealias PrefEditor = SharedPreferences.Editor

fun SharedPreferences.boolean(
    key: String,
    defaultValue: Boolean = false
): Boolean {
    return getBoolean(key, defaultValue)
}

inline fun SharedPreferences.commit(crossinline exec: PrefEditor.() -> Unit) {
    val editor = this.edit()
    editor.exec()
    editor.apply()
}

fun Int.toHex() = "#${Integer.toHexString(this)}"

fun Calendar.formatTime(): String {
    return SimpleDateFormat("kk:mm a", Locale.US).format(this.time)
}

fun Calendar.formatDate(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.time)
}

fun Calendar.formatDateTime(): String {
    return SimpleDateFormat("kk:mm a, MMMM dd, yyyy", Locale.US).format(this.time)
}


/**
 * 递归地把控件的enable 状态设置为指定值
 */
fun View?.setEnableStateRecursively(enable: Boolean) {
    if (this == null) return
    if (this is ViewGroup) {
        // 递归子控件
//        this.forEach {
//            it.setEnableStateRecursively(enable)
//        }

        for (index in 1..childCount) {

        }


    }

    // 处理当前控件
    this.isEnabled = enable
}

private val sUiThreadHandler = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行代码
 */
fun runOnUiThread(action: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action.invoke()
    } else {
        sUiThreadHandler.post(action)
    }
}

fun runOnUiThreadDelay(action: () -> Unit, dealy: Long) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        sUiThreadHandler.postDelayed({
            action.invoke()
        }, dealy)


    } else {
        sUiThreadHandler.postDelayed(action, dealy)
    }
}

/**
 * 在子线程中运行代码
 */
fun runOnBackground(action: () -> Unit) {
}

fun removeAllMessageOnUIThreadQueue() {
    sUiThreadHandler.removeCallbacksAndMessages(null)
}

/**
 * View是否展示在界面上
 */
fun View.isDisplayed(): Boolean {
    return getGlobalVisibleRect(Rect()) && withEffectiveVisibility(this)
}

/**
 * 是否有效展示
 */
fun withEffectiveVisibility(view: View): Boolean {
    return if (view.visibility != View.VISIBLE) {
        false
    } else {
        if (view.parent is View) {
            withEffectiveVisibility(view.parent as View)
        } else {
            true
        }
    }
}

inline fun View.removeSelf(): Boolean {
    if (this.parent != null && this.parent is ViewGroup) {
        (this.parent as ViewGroup).removeView(this)
        return true
    }
    return false
}


fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

//扩展函数，view显示
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

var View.isVisible:Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }



fun RecyclerView.removeDecorations() {
    for (i in 0 until itemDecorationCount) {
        removeItemDecorationAt(i)
    }
}


operator fun ViewGroup.plusAssign(other: View) = addView(other)


suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->

    // 这里的 lambda 表达式会被立即调用，允许我们创建一个监听器
    val listener = object : View.OnLayoutChangeListener {

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            // 视图的下一次布局任务被调用
            // 先移除监听，防止协程泄漏
            removeOnLayoutChangeListener(this)
            // 最终，唤醒协程，恢复执行
            cont.resume(Unit, null)
        }
    }
    // 如果协程被取消，移除该监听
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    // 最终，将监听添加到 view 上
    addOnLayoutChangeListener(listener)

    // 这样协程就被挂起了，除非监听器中的 cont.resume() 方法被调用

}


suspend fun Animator.awaitEnd() = suspendCancellableCoroutine<Unit> { cont ->

    // 增加一个处理协程取消的监听器，如果协程被取消，
    // 同时执行动画监听器的 onAnimationCancel() 方法，取消动画
    cont.invokeOnCancellation { cancel() }

    addListener(object : AnimatorListenerAdapter() {
        private var endedSuccessfully = true

        override fun onAnimationCancel(animation: Animator) {
            // 动画已经被取消，修改是否成功结束的标志
            endedSuccessfully = false
        }

        override fun onAnimationEnd(animation: Animator) {

            // 为了在协程恢复后的不发生泄漏，需要确保移除监听
            animation.removeListener(this)
            if (cont.isActive) {

                // 如果协程仍处于活跃状态
                if (endedSuccessfully) {
                    // 并且动画正常结束，恢复协程
                    cont.resume(Unit, null)
                } else {
                    // 否则动画被取消，同时取消协程
                    cont.cancel()
                }
            }
        }
    })
}


fun RecyclerView.clearDecorations() {
    if (itemDecorationCount > 0) {
        for (i in itemDecorationCount - 1 downTo 0) {
            removeItemDecorationAt(i)
        }
    }
}

fun View.setBackground(drawable: Drawable) {
    ViewCompat.setBackground(this, drawable)
}


fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
//                EspressoIdlingResource.increment()
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//                EspressoIdlingResource.decrement()
            }
        })
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(
        lifecycleOwner,
        Observer { event ->
            event.getContentIfNotHandled()?.let {
                showSnackbar(context.getString(it), timeLength)
            }
        }
    )
}


fun Paint.textCenterX(text: String, orginalCenterX: Float): Float {
    val rect = Rect()
    getTextBounds(text, 0, text.length, rect)
    return (orginalCenterX - rect.width() / 2).toFloat()
}

/**
 *
 * @receiver Paint
 * @param text String
 * @param baseY Float 矩形中间位置
 * @return Float
 */
fun Paint.textCenterY(text: String, baseY: Float): Float {
    val rect = Rect()
    getTextBounds(text, 0, text.length, rect)
    return baseY + rect.height() / 2
}

fun Paint.textCenterY2(text: String, baseY: Float): Float {
    val centerY = baseY + (fontMetrics.bottom - fontMetrics.top) /2 - fontMetrics.bottom
    return centerY
}

fun Paint.textCenterY3(text: String, baseY: Float): Float {
    measureText(text)
    val distance = abs(ascent() + descent()) / 2
    return baseY + distance
}



val View.inScreen:Boolean
    get() {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels

        val screenRect = Rect(0,0,screenWidth,screenHeight)
        val array = IntArray(2)
        getLocationOnScreen(array)
        //获取视图矩形
        val viewRect = Rect(array[0],array[1],array[0] + width,array[1] + height)
        //判断屏幕和视图举行是否有交集
        return screenRect.intersect(viewRect)
    }


//协程和生命周期绑定
/**
 * 为 Job 扩展凡是,传入 View 类型的参数，表示该 Job 和 View 的生命周期绑定，当
 * View 生命周期结束时，自动取消协程
 */
fun Job.autoDispose(view: View){
    val isAttached = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow
            || view.windowToken != null

    if (!isAttached){
        cancel()
    }
    val listener = object :View.OnAttachStateChangeListener{
        override fun onViewAttachedToWindow(v: View) {
        }

        override fun onViewDetachedFromWindow(v: View) {
            cancel()
            v?.removeOnAttachStateChangeListener(this)
        }

    }
    view.addOnAttachStateChangeListener(listener)
    invokeOnCompletion {
        view.removeOnAttachStateChangeListener(listener)
    }

}

/**
 *  获取 DecoreView
 */
val Activity.decoreView:FrameLayout?
    get() = takeIf { !isFinishing && !isDestroyed  }?.window?.decorView as? FrameLayout?

/**
 * RecycleView 添加点击事件处理
 */
fun RecyclerView.addOnItemClickListener(listener:(View, Int) ->Unit = {_,_ -> } ){

    addOnItemTouchListener(object :RecyclerView.OnItemTouchListener{

        val gestureDetector = GestureDetector(context,object :GestureDetector.OnGestureListener{
            override fun onDown(e: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(e: MotionEvent) {
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                //当单击事件发生时，寻找单击坐标下的控件，并回调监听器
                e.let {
                    findChildViewUnder(it.x,it.y)?.let { child ->
                        listener(child,getChildAdapterPosition(child))
                    }
                }
                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent) {
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }

        })



        //'在拦截触摸事件时，解析触摸事件'
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }

    })

}


 fun getActivityFromView(view: View): Activity? {
    var context = view.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

//写一个flow型的扩展函数
/* fun TextView.textViewFlow() = callbackFlow {
    //edit监听 相当于新建一个接口对象
    val textWatch = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            Log.d("ArticleFragment", s.toString())
            // this@callbackFlow.trySend(s.toString()).isSuccess
        }
    }
    addTextChangedListener(textWatch)
    awaitClose {
        removeTextChangedListener(textWatch)
    }
} */


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showOrHide(show: Boolean) = if (show) show() else hide()

fun View.dimenFloat(@DimenRes res: Int) = resources.getDimension(res)

fun View.dimenInt(@DimenRes res: Int) = resources.getDimensionPixelSize(res)


fun Activity.finishWithErrorToast(@StringRes errorRes: Int, vararg formatArgs: String) {
    val text = getString(errorRes, *formatArgs)
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    finish()
}



/**
 * Update the texts of a view.
 *
 * @param resId  the id of the text to update
 * @param setTextFunction function which updates the text of the view T
 * @return the transformed view.
 */
fun View.updateTexts(resId: Int, setTextFunction: (CharSequence) -> Unit) {
    setTextFunction(resources.getText(resId))
}



/**
 * ViewPager选中
 */
fun ViewPager.doSelected(selected: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            selected.invoke(position)
        }

    })
}




inline fun FragmentActivity.fragmentTransactionWithBackStack(
    name: String? = null,
    crossinline block: FragmentTransaction.() -> Unit,
) {
    fragmentTransaction {
        block()
        addToBackStack(name)
    }
}

@Suppress("DEPRECATION")
fun TextView.setTextAppearanceCompat(resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        setTextAppearance(resId)
    } else {
        setTextAppearance(context, resId)
    }
}

fun Context.getDrawableOrNull(@DrawableRes id: Int?): Drawable? {
    return if (id == null || id == 0) null else AppCompatResources.getDrawable(this, id)
}


fun release(root:ViewGroup){
    val childCount = root.childCount
    for (i in 0 until childCount){
        val child = root.getChildAt(i)
        if (child is ViewGroup){
            child.setBackground(null)
            release(child)
        }else{
            child?.background = null
            if (child is ImageView){
                child.setImageDrawable(null)
            }else if(child is EditText){
//                child.cleanWatchers()
            }
        }
    }
}


fun View.getString(@StringRes stringRes: Int): String = context.getString(stringRes)

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(this.context)




internal fun getRootView(view: View): View {
    var rootView = view
    var parent = rootView.parent
    while (parent is View) {
        rootView = parent
        parent = rootView.parent
    }
    return rootView
}

fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    val bundle = arguments ?: Bundle()
    argsBuilder.invoke(bundle)
    arguments = bundle
    return this
}

fun Fragment.setStatusBarColor(@ColorRes statusBarColor: Int?) {
    if (statusBarColor == null) {
        return
    }
    val contextNotNull = context ?: return
    val window: Window = activity?.window ?: return
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(contextNotNull, statusBarColor)
}


fun <F : Fragment> View.findFragment(): F = FragmentManager.findFragment(this)

fun Animator.release(){
    this?.let {
        it.removeAllListeners()
        it.cancel()
    }
}