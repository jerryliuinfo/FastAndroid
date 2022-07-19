package com.apache.fastandroid.demo.drakeet.aboutpage

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.R
import com.drakeet.about.*
import com.tesla.framework.kt.showToast

/**
 * Created by Jerry on 2022/6/27.
 */
class AboutPageDemoActivity:AbsAboutActivity(), OnRecommendationClickedListener,
    OnContributorClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRecommendationClickedListener = this
        onContributorClickedListener = this
    }


    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = "About Page By drakeet"
        version.text = "v" + BuildConfig.VERSION_NAME
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.add(Category("介绍与帮助"))
        //内容
        //内容
        items.add(Card(getString(R.string.card_content)))

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
                0, getString(R.string.pure_writer),
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
                1, getString(R.string.pure_mosaic),
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

    override fun onRecommendationClicked(itemView: View, recommendation: Recommendation): Boolean {
        showToast("onRecommendationClicked: " + recommendation.appName)
        return false
    }

    override fun onContributorClicked(itemView: View, contributor: Contributor): Boolean {
        showToast("onContributorClicked: " + contributor.name)
        return false
    }
}