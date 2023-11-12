package com.apache.fastandroid.demo.drakeet.customview.sample.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.drakeet.insets.EdgeInsetDelegate
import com.apache.fastandroid.demo.drakeet.insets.InsetsAnimationCustomLayout
import com.apache.fastandroid.demo.drakeet.insets.doOnApplyWindowInsets
import com.apache.fastandroid.demo.drakeet.insets.translateDeferringInsetsAnimation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.kt.getAttrColor
import com.tesla.framework.kt.toTheme

class ChatActivity : AppCompatActivity() {

  private val edgeInsetDelegate by lazy(LazyThreadSafetyMode.NONE) {
    EdgeInsetDelegate(this)
  }

  @SuppressLint("WrongConstant")
  @Suppress("DEPRECATION")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    ViewCompat.getWindowInsetsController(window.decorView)?.let {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        it.show(WindowInsets.Type.systemBars())
      }
      it.isAppearanceLightStatusBars = true
      it.isAppearanceLightNavigationBars = true
    }

    val contentView = ChatLayout(this)
    setContentView(contentView)

    edgeInsetDelegate.start()

    with(contentView.recyclerView) {
      layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL, true)
      adapter = ChatMsgAdapter()
    }

    with(contentView) {
      doOnApplyWindowInsets { windowInsets, padding, _, deferredInsets ->
        val type = when {
          deferredInsets -> WindowInsetsCompat.Type.systemBars()
          else -> WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        }
        val insets = windowInsets.getInsets(type)
        updatePadding(
          top = padding.top + insets.top,
          bottom = padding.bottom + insets.bottom
        )
      }

      messageHolder.translateDeferringInsetsAnimation(DISPATCH_MODE_CONTINUE_ON_SUBTREE)
      recyclerView.translateDeferringInsetsAnimation()
    }
  }
}

@SuppressLint("SetTextI18n")
class ChatLayout(context: Context) : InsetsAnimationCustomLayout(context) {

  @JvmField
  val toolbar = MaterialToolbar(
    context.toTheme(R.style.Base_Theme_Toolbar),
    null,
    R.attr.toolbarStyle
  ).autoAddView(MATCH_PARENT, WRAP_CONTENT) {
    elevation = 4.dp.toFloat()
  }

  @JvmField
  val recyclerView = RecyclerView(context).autoAddView(width = MATCH_PARENT)

  @JvmField
  val messageHolder = ChatMessageHolderView(context).autoAddView(MATCH_PARENT, 48.dp)

  init {
    toolbar.addView(ImageView(context).apply {
      setImageResource(R.drawable.ic_baseline_face_24)
    })
    toolbar.addView(TextView(context).apply {
      text = " IME Animations"
    })
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    toolbar.autoMeasure()
    messageHolder.autoMeasure()

    val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
    //计算 recycleview 高度
    val recyclerViewHeight = (parentHeight
      - toolbar.measuredHeight - messageHolder.measuredHeight
      - paddingTop - paddingBottom)
    recyclerView.autoMeasure(heightMeasureSpec = recyclerViewHeight.toExactlyMeasureSpec())
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    layoutVertical(paddingTop, toolbar, recyclerView, messageHolder)
  }
}

//底部的输入框
class ChatMessageHolderView(context: Context) : CustomLayout(context) {



  @JvmField
  val input = TextInputLayout(
    context.toTheme(R.style.Base_Theme_InputLayout),
    null,
    R.attr.textInputStyle
  ).autoAddView(height = WRAP_CONTENT)


  @JvmField
  val inputEdit = AppCompatEditText(context).apply {
    hint = "Type a message..."
  }

  @JvmField
  val btnSend = ImageButton(
    context.toTheme(R.style.Base_Theme_SendButton),
    null,
    R.attr.imageButtonStyle
  ).autoAddView(48.dp) {
    setImageResource(R.drawable.ic_baseline_send_24)
  }

  init {
    input.addView(inputEdit)
    setBackgroundColor(context.getAttrColor(R.attr.colorSurface))
    elevation = 4.dp.toFloat()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    //先测量 send 按钮，再测量 input 的宽度
    btnSend.autoMeasure()

    val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
    val inputWidth = parentWidth - btnSend.measuredWidth
    input.autoMeasure(widthMeasureSpec = inputWidth.toExactlyMeasureSpec())

    setMeasuredDimension(widthMeasureSpec, input.measuredHeight.toExactlyMeasureSpec())
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    input.layout(0, 0)
    //按钮在输入框的右边
    btnSend.layout(input.measuredWidth, 0)
  }
}