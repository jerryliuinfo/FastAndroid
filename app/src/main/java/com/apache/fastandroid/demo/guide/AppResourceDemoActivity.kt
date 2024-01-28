package com.apache.fastandroid.demo.guide

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Spanned
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityAppResourcesBinding
import com.apache.fastandroid.demo.guide.appresource.drawable.DrawableResDemoFragment
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.ui.activity.BaseBindingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

/**
 * https://developer.android.com/training/basics/firstapp?hl=zh-cn
 */

class AppResourceDemoActivity : BaseBindingActivity<ActivityAppResourcesBinding>() {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.btnRuntimeChange.setOnClickListener {
            Logger.d("旋转屏幕执行 onConfigurationChanged")
        }

        mBinding.btnPartlyTranslate.setOnClickListener {
            partlyTranslate()
        }

        mBinding.btnFindLanguageByContext.setOnClickListener {
            findLanguageByContext()
        }

        mBinding.btnChangeLanguageByMonitor.setOnClickListener {
            changeLanguageByAdb()
        }

        mBinding.btnAnimator.setOnClickListener {
            animatorUsage()
        }

        mBinding.btnAnim.setOnClickListener {
            animUsage()
        }

        mBinding.btnAnimationList.setOnClickListener {
            animationListUsage()
        }

        mBinding.btnQuantityPlurals.setOnClickListener {
            quantityPluralsUsage()
        }

        mBinding.btnStringFormat.setOnClickListener {
            stringResUsage()
        }

        mBinding.btnStringHtml.setOnClickListener {
            stringHtmlUsage()
        }

        mBinding.btnFont.setOnClickListener {
            fontUsage()
        }

        mBinding.btnDrawable.setOnClickListener {
            launchFragment<DrawableResDemoFragment>()
        }

    }

    private fun fontUsage() {
        val text = mBinding.tvFont.text
        Logger.d("fontUsage text:$text")
    }

    private fun stringHtmlUsage() {
        val text: String = getString(R.string.str_html)
        val styledText: Spanned = CommonUtil.fromHtml(text)
        mBinding.btnResult.text = styledText
    }

    private fun stringResUsage() {
        val text = getString(R.string.welcome_messages, "Jim", 2)
        mBinding.btnResult.text = text
    }

    /**
     * https://developer.android.com/guide/topics/resources/string-resource?hl=zh-cn
     */
    private fun quantityPluralsUsage() {
        lifecycleScope.launch {
            var songsFound = resources.getQuantityString(R.plurals.numberOfSongsAvailable, 1, 1)
            Logger.d("songsFound for one:$songsFound")
            mBinding.btnResult.text = songsFound
            delay(1000)
            songsFound = resources.getQuantityString(R.plurals.numberOfSongsAvailable, 2, 2)
            Logger.d("songsFound for two:$songsFound")
            mBinding.btnResult.text = songsFound
        }
    }


    private fun animationListUsage() {
        mBinding.avator.setBackgroundResource(R.drawable.rocket_thrust)
        val rocketAnimation = mBinding.avator.background
        if (rocketAnimation is Animatable) {
            rocketAnimation.start()
        }
    }

    private fun animUsage() {
        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(this, R.anim.property_anim)
        mBinding.btnResult.startAnimation(hyperspaceJump)
    }

    private fun animatorUsage() {
        val set: AnimatorSet =
            AnimatorInflater.loadAnimator(this, R.animator.property_animator) as AnimatorSet
        set.apply {
            setTarget(mBinding.btnRuntimeChange)
            start()
        }
    }

    private fun changeLanguageByAdb() {
        // 用加拿大法语进行测试，请运行以下命令
        val command = "setprop persist.sys.locale fr-CA;stop;sleep 5;start"


    }

    private fun findLanguageByContext() {
        val primaryLocale: Locale = resources.configuration.locales[0]
        Logger.d("displayName:${primaryLocale.displayName},displayLanguage:${primaryLocale.displayLanguage},displayCountry:${primaryLocale.displayCountry}")
        mBinding.btnResult.text = primaryLocale.displayName
    }

    private fun partlyTranslate() {
        lifecycleScope.launch {
            mBinding.btnResult.setText(R.string.countdown)
            delay(1000)
            mBinding.btnResult.setText(R.string.star_rating)
            delay(1000)
            mBinding.btnResult.setText(R.string.app_homeurl)
            delay(1000)
            mBinding.btnResult.setText(R.string.prod_name)
            delay(1000)
            mBinding.btnResult.setText(R.string.promo_message)
        }
    }

    private fun buildCreateCalendarIntent() {
        val calendarIntent =
            Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val beginTime: Calendar = Calendar.getInstance().apply {
                    set(2021, 0, 23, 7, 30)
                }
                val endTime = Calendar.getInstance().apply {
                    set(2021, 0, 23, 10, 30)
                }
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "Ninja class")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo")
            }
        startActivity(calendarIntent)
    }

    private fun buildQueryWebPage() {
        val webIntent: Intent = Uri.parse("https://www.android.com").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

    private fun buildDialPhoneIntent() {
        val callIntent = Uri.parse("tel:5551234").let { number ->
            Intent(Intent.ACTION_DIAL, number)
        }
        startActivity(callIntent)
    }

    private fun buildQueryMapIntent() {
        val mapIntent: Intent = Uri.parse(
            "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
        ).let { location ->
            // Or map point based on latitude/longitude
            // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }
        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
        }
    }

    /***
     * 在 View 系统中，如果发生配置变更并且您已停用 Activity 重新创建功能，activity 会收到对 Activity.onConfigurationChanged() 的调用。任何关联的视图也会收到对 View.onConfigurationChanged() 的调用
     * 在 Android 11（API 级别 30）到 Android 13（API 级别 33）中，系统不会调用 Activity.onConfigurationChanged()。
     * @param newConfig Configuration
     */

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.d("onConfigurationChanged AppResourceDemoActivity")
        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            showToast("Keyboard available")
        } else if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            showToast("No keyboard")

        }
    }

}