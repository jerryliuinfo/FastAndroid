package com.apache.fastandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.apache.fastandroid.databinding.ViewMainItemContentBinding
import kotlinx.android.synthetic.main.view_main_item_content.view.descriptionView
import kotlinx.android.synthetic.main.view_main_item_content.view.imageView
import kotlinx.android.synthetic.main.view_main_item_content.view.titleView

class MainItemView<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val mBinding = ViewMainItemContentBinding.inflate(LayoutInflater.from(context), this)


    var callback: Callback<T?>? = null
    var item: T? = null

    interface Callback<T> {
        fun onClick(item: T?)
        fun onLongClick(item: T?): Boolean
    }


    init {
        mBinding.imageView.apply {
            setOnClickListener {
                callback?.onClick(item)
            }
            setOnLongClickListener {
                callback?.onLongClick(item)
                false
            }
        }
    }


    var description: CharSequence
        get() = descriptionView.text
        set(value) {
            descriptionView.text = value
        }

    fun setTitle(value: String) {
        titleView.text = value
    }

    fun setDesc(value: String) {
        descriptionView.text = value
    }

    fun setImage(@DrawableRes value: Int) {
        imageView.setImageResource(value)
    }

    fun setImageResource(@DrawableRes value: Int) {
        imageView.setImageResource(value)
    }
}
