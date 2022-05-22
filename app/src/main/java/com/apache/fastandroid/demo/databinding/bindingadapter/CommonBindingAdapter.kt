/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apache.fastandroid.demo.databinding.bindingadapter

import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import com.apache.fastandroid.R
import com.blankj.utilcode.util.ClickUtils
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tesla.framework.common.util.log.NLog

/**
 * Create by KunMinX at 19/9/18
 */
private const val TAG = "CommonBindingAdapter"

/**
 * 注意 kotlin 的 databinding 扩展方法只能定义成top level function，不能写在类里面
 *会报 A BindingAdapter in  is not static and requires an object to use, retrieved from the DataBindingComponent. If you don't use an inflation method taking a DataBindingComponent, use DataBindingUtil.setDefaultComponent or make all BindingAdapter methods static.         at android.app.ActivityThread.handleBindApplication(ActivityThread.jav
 * 参考:https://stackoverflow.com/questions/40085724/kotlin-custom-attribute-databinding
 */
//object CommonBindingAdapter {

    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        text?.let {
            Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
        /*Glide.with(view.context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteCard.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            ).into(view)*/
    }

    @BindingAdapter("onBackPressed")
    fun bindOnBackPressed(view: View, onBackPress: Boolean) {
        val context = view.context
        if (onBackPress && context is OnBackPressedDispatcherOwner) {
            view.setOnClickListener {
                context.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun imageUrl(view: ImageView, url: String?, placeHolder: Drawable?) {
        NLog.d(TAG, "imageUrl: %s", url)
        Glide.with(view.context).load(url).into(view)
    }

    @BindingAdapter(value = ["visible"], requireAll = false)
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("isGone")
    fun bindIsGone(view: View, isGone: Boolean) {
        view.visibility = if (isGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


    @BindingAdapter(value = ["selected"], requireAll = false)
    fun selected(view: View, select: Boolean) {
        view.isSelected = select
    }

    @BindingAdapter(value = ["onClickWithDebouncing"], requireAll = false)
    fun onClickWithDebouncing(view: View?, clickListener: View.OnClickListener?) {
        ClickUtils.applySingleDebouncing(view, clickListener)
    }


    @BindingAdapter(value = ["isOpenDrawer"], requireAll = false)
    fun openDrawer(drawerLayout: DrawerLayout, isOpenDrawer: Boolean) {
        if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START)
        } else {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    @BindingAdapter(value = ["allowDrawerOpen"], requireAll = false)
    fun allowDrawerOpen(drawerLayout: DrawerLayout, allowDrawerOpen: Boolean) {
        drawerLayout.setDrawerLockMode(if (allowDrawerOpen) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    @BindingAdapter(value = ["bindDrawerListener"], requireAll = false)
    fun listenDrawerState(drawerLayout: DrawerLayout, listener: SimpleDrawerListener?) {
        drawerLayout.addDrawerListener(listener!!)
    }


    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }

    @BindingAdapter("isFabGone")
    fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
        if (isGone == null || isGone) {
            view.hide()
        } else {
            view.show()
        }
    }

    @BindingAdapter("renderHtml")
    fun bindRenderHtml(view: TextView, description: String?) {
        if (description != null) {
            view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            view.movementMethod = LinkMovementMethod.getInstance()
        } else {
            view.text = ""
        }
    }

    @BindingAdapter("wateringText")
    fun bindWateringText(textView: TextView, wateringInterval: Int) {
        val resources = textView.context.resources
        val quantityString = resources.getQuantityString(
            R.plurals.watering_needs_suffix,
            wateringInterval,
            wateringInterval
        )

        textView.text = quantityString
    }

    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
//}



@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}