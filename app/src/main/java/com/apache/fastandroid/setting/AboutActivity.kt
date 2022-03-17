package com.apache.fastandroid.setting

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.apache.fastandroid.BuildConfig
import com.drakeet.about.*
import com.apache.fastandroid.R

import com.drakeet.about.provided.PicassoImageLoader

/**
 * Created by Jerry on 2022/3/16.
 */
class AboutActivity: AbsAboutActivity(), OnRecommendationClickedListener, OnContributorClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setImageLoader(PicassoImageLoader())
        onRecommendationClickedListener = this
        onContributorClickedListener = this
    }

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = "About Page By drakeet"
        version.text = "v" + BuildConfig.VERSION_NAME
    }

    override fun onItemsCreated(items: MutableList<Any?>) {
        items.add(Category("介绍与帮助"))
        items.add(Card("这是一个快速开发的框架"))
        items.add(Category("Developers"))
        items.add(
            Contributor(
                R.drawable.avatar_drakeet,
                "Drakeet",
                "Developer & designer",
                "http://weibo.com/drak11t"
            )
        )
        items.add(Contributor(R.drawable.avatar_drakeet, "黑猫酱", "Developer", "https://drakeet.me"))
        items.add(Contributor(R.drawable.avatar_drakeet, "小艾大人", "Developer"))
        items.add(Category("我独立开发的应用"))
        items.add(
            Recommendation(
                0, "FastAndroid",
                "https://raw.githubusercontent.com/PureWriter/about-page/master/images/app_writer.png",
                "com.drakeet.purewriter",
                "快速的纯文本编辑器，我们希望写作能够回到原本的样子：纯粹、有安全感、随时、绝对不丢失内容、具备良好的写作体验。",
                "https://play.google.com/store/apps/details?id=com.drakeet.purewriter",
                "2017-10-09 16:46:57",
                "2017-10-09 16:46:57", 2.93, true
            )
        )
        items.add(
            Recommendation(
                1, getString(R.string.app_name),
                "https://raw.githubusercontent.com/PureWriter/about-page/master/images/app_mosaic.png",
                "me.drakeet.puremosaic",
                "专注打码的轻应用，包含功能：传统马赛克、毛玻璃效果、选区和手指模式打码，更有创新型高亮打码和 LowPoly 风格马赛克。只为满足一个纯纯的打码需求，让打码也能成为一种赏心悦目。",
                "https://play.google.com/store/apps/details?id=me.drakeet.puremosaic",
                "2017-10-09 16:46:57",
                "2017-10-09 16:46:57", 2.64, true
            )
        )
        items.add(Category("Open Source Licenses"))
        items.add(
            License(
                "about-page",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/about-page"
            )
        )
        items.add(
            License(
                "MultiType",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/MultiType"
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        val dayNight = menu.findItem(R.id.menu_night_mode)
        dayNight.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_night_mode) {
            menuItem.isChecked = !menuItem.isChecked
            if (menuItem.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            delegate.applyDayNight()
        }
        return true
    }

    override fun onRecommendationClicked(itemView: View, recommendation: Recommendation): Boolean {
        Toast.makeText(
            this,
            "onRecommendationClicked: " + recommendation.appName,
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    override fun onContributorClicked(itemView: View, contributor: Contributor): Boolean {
        if (contributor.name == "小艾大人") {
            Toast.makeText(this, "onContributorClicked: " + contributor.name, Toast.LENGTH_SHORT)
                .show()
            return true
        }
        return false
    }
}