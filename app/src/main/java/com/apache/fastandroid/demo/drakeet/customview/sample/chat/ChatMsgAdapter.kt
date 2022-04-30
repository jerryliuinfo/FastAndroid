package com.apache.fastandroid.demo.drakeet.customview.sample.chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.common.util.DrawableUtils

class ChatMsgAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun getItemCount(): Int = 25

  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) ITEM_TYPE_MESSAGE_OTHER else ITEM_TYPE_MESSAGE_SELF
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = when (viewType) {
      ITEM_TYPE_MESSAGE_SELF -> ChatMsgBubbleSelfView(parent.context)
      else -> ChatMsgBubbleOtherView(parent.context)
    }
    return MessageHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

  }

  companion object {
    private const val ITEM_TYPE_MESSAGE_SELF = 0
    private const val ITEM_TYPE_MESSAGE_OTHER = 1
  }
}

private class MessageHolder(view: View) : RecyclerView.ViewHolder(view)

@SuppressLint("SetTextI18n")
class ChatMsgBubbleOtherView(context: Context) : CustomLayout(context) {

  @JvmField
  val bubbleMessage = TextView(context).autoAddView {
    setPadding(10.dp)
    setTextColor(Color.WHITE)
    background = DrawableUtils.createRadiusDrawable(
      Color.DKGRAY,
      topLeft = 0,
      topRight = 8.dp,
      bottomRight = 8.dp,
      bottomLeft = 8.dp
    )
  }

  init {
    bubbleMessage.text = "To me"
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    bubbleMessage.autoMeasure()
    setMeasuredDimension(
      widthMeasureSpec,
      (bubbleMessage.measuredHeight
        + bubbleMessage.paddingTop
        + bubbleMessage.paddingBottom).toExactlyMeasureSpec()
    )
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    bubbleMessage.layout(10.dp, 10.dp)
  }
}

@SuppressLint("SetTextI18n")
class ChatMsgBubbleSelfView(context: Context) : CustomLayout(context) {

  @JvmField
  val bubbleMessage = TextView(context).autoAddView {
    setPadding(10.dp)
    setTextColor(Color.WHITE)
    background = DrawableUtils.createRadiusDrawable(
      Color.BLUE,
      topLeft = 8.dp,
      topRight = 0,
      bottomRight = 8.dp,
      bottomLeft = 8.dp
    )
  }

  init {
    bubbleMessage.text = "To you"
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    bubbleMessage.autoMeasure()
    setMeasuredDimension(
      widthMeasureSpec,
      (bubbleMessage.measuredHeight
        + bubbleMessage.paddingTop
        + bubbleMessage.paddingBottom).toExactlyMeasureSpec()
    )
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    bubbleMessage.layout(10.dp, 10.dp, fromRight = true)
  }
}