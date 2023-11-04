package com.apache.fastandroid.demo.drakeet.customview.sample.course

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.apache.fastandroid.R
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.component.imageloader.ImageLoaderManager
//顶部布局
class OnlineTopLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomLayout(context, attrs) {

    @JvmField
    val musicLogo = ImageView(context).autoAddView(17.5f.dp) {
        it.leftMargin = 10.dp
    }

    @JvmField
    val musicTitle = TextView(context).autoAddView {
        setTextColor(Color.WHITE)
        setTextSizePx(15.sp)
        maxLines = 1
        paint.isFakeBoldText = true
        //距离左边 10 dp
        it.leftMargin = 10.dp
    }
    //礼物背景view
    private val giftBgView = ImageView(context).autoAddView(height = 27.dp)

    @JvmField
    val giftFlowerText = createGiftText(context)
    private val giftFlowerLogo = createGiftLogo(context).apply {
        ImageLoaderManager.load(this, R.mipmap.ic_gift_flower)

    }
    private val giftFlowerLine = createGiftLine(context)

    @JvmField
    val giftCrownText = createGiftText(context)
    private val giftCrownLogo = createGiftLogo(context)
    private val giftCrownLine = createGiftLine(context)

    @JvmField
    val giftMikeText = createGiftText(context)
    private val giftMikeLogo = createGiftLogo(context)
    private val giftMikeLine = createGiftLine(context)

    @JvmField
    val giftCarText = createGiftText(context)
    private val giftCarLogo = createGiftLogo(context)
    private val giftCarLine = createGiftLine(context)

    @JvmField
    val giftPlaneText = createGiftText(context)
    private val giftPlaneLogo = createGiftLogo(context)

    private val giftViews = arrayOf(
        giftFlowerLogo, giftFlowerText, giftFlowerLine,
        giftCrownLogo, giftCrownText, giftCrownLine,
        giftMikeLogo, giftMikeText, giftMikeLine,
        giftCarLogo, giftCarText, giftCarLine,
        giftPlaneLogo, giftPlaneText
    )

    init {
        //背景颜色
        ImageLoaderManager.load(this, "#592FD1")
        ImageLoaderManager.load(musicLogo, R.mipmap.ic_attend_music)
        ImageLoaderManager.load(giftBgView, "#4B1FA9", 8.dp)
//        ImageLoaderManager.load(giftFlowerLogo, R.mipmap.ic_gift_flower)
        ImageLoaderManager.load(giftCrownLogo, R.mipmap.ic_gift_crown)
        ImageLoaderManager.load(giftMikeLogo, R.mipmap.ic_gift_mike)
        ImageLoaderManager.load(giftCarLogo, R.mipmap.ic_gift_car)
        ImageLoaderManager.load(giftPlaneLogo, R.mipmap.ic_gift_plane)
        musicTitle.text = "音乐欣赏"
        giftFlowerText.text = "8"
        giftCrownText.text = "8"
        giftMikeText.text = "8"
        giftCarText.text = "8"
        giftPlaneText.text = "8"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        musicLogo.autoMeasure()
        musicTitle.autoMeasure()
        autoMeasure(*giftViews)
        giftBgView.autoMeasure(
            widthMeasureSpec = plusWidthWithMargins(*giftViews).toExactlyMeasureSpec()
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //多 view 横向排列 垂直居中
        layoutHorizontal(musicLogo, musicTitle)
        //距离 右边 10 dp
        giftBgView.layoutHorizontal(10.dp, fromRight = true)
        //摆放礼物
        layoutHorizontal(startX = giftBgView.left, *giftViews)
    }

    private fun createGiftLogo(context: Context) =
        ImageView(context).autoAddView(20.dp) {
            it.leftMargin = 10.dp
        }

    private fun createGiftText(context: Context) =
        TextView(context).autoAddView {
            setTextColor(Color.WHITE)
            setTextSizePx(13.sp)
            maxLines = 1
            it.leftMargin = 5.dp
            it.rightMargin = 10.dp
        }

    private fun createGiftLine(context: Context) =
        View(context).autoAddView(0.5f.dp, 12.dp) {
            setBackgroundColor(Color.WHITE)
        }
}