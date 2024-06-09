package com.apache.fastandroid.jetpack.livedata

import android.app.Activity
import android.os.Bundle
import com.apache.fastandroid.databinding.FragmentJetpackLivedataWrongUsageBinding
import com.apache.fastandroid.jetpack.relearnandroid.vm.ShareViewModel
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVBActivity

import com.tesla.framework.ui.activity.FragmentContainerActivity

/**
 * Created by Jerry on 2020/11/5.
 *  //https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
class LiveDataWrongUsageActivity : BaseVBActivity<FragmentJetpackLivedataWrongUsageBinding>(FragmentJetpackLivedataWrongUsageBinding::inflate){


    private lateinit var shareViewModel: ShareViewModel


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        shareViewModel = getActivityViewModel(ShareViewModel::class.java)

        Logger.d("LiveDataWrongUsageFragment userInfoViewModel :${shareViewModel}")

        byNormal()

        byNormalRestValue()

        bySingleEvent()

        byEventWrapper()


    }

    /**
     * Bad: 1. 错误用法1: 用livedata 监听做界面跳转，跳转到详情页面，按返回键回到当前页面，然后旋转屏幕，由于Livedata的粘性特性，
     * 又会收到onChnage回调导致再次跳转到详情页面，不符合预期
     */
    private fun byNormal(){
        mBinding.btnEventNormal.setOnClickListener {
            shareViewModel.goToDetail()
        }
        shareViewModel.navigationToDetail.observe(this){
             Logger.d("LiveDataWrongUsageFragment byNormal observer:${it}")
             if (it){
                 FragmentContainerActivity.launch(this@LiveDataWrongUsageActivity,LiveDataDetailFragment::class.java,null)
             }
        }
    }


    /**
     * good: 2. resetting event values in observer
     * 在收到监听后，设置重置livedata数据
     * 这种方法的问题是有一些样板（每个事件在 ViewModel 中有一个新方法）并且容易出错；很容易忘记观察者对 ViewModel 的调用。
     */
    private fun byNormalRestValue(){
        mBinding.btnEventNormal2.setOnClickListener {
            shareViewModel.goToDetail2()
        }
        shareViewModel.navigationToDetail2.observe(this){
            Logger.d("LiveDataWrongUsageFragment byNormalRestValue onChanged:${it}")
            shareViewModel.navigationHasHanded()
            if (it){
                FragmentContainerActivity.launch(this@LiveDataWrongUsageActivity,LiveDataDetailFragment::class.java,null)
            }
        }
    }


    /**
     * OK: Use SingleLiveEvent
     * 缺点:SingleLiveEvent 的问题在于它仅限于一个观察者。如果您无意中添加了多个，则只会调用一个，并且无法保证是哪一个。
     *
     */
    private fun bySingleEvent(){
        mBinding.btnSingleEvent.setOnClickListener {
            shareViewModel.goToDetailBySingleEvent()
        }

        shareViewModel.navigationToDetailSingleEvent.observe(this){
            println("LiveDataWrongUsageFragment22 bySingleEvent onChanged:${it}")
//            FragmentContainerActivity.launch(this@LiveDataWrongUsageActivity,LiveDataDetailFragment::class.java,null)
        }
        shareViewModel.navigationToDetailSingleEvent.observe(this){
            println("LiveDataWrongUsageFragment11 bySingleEvent onChanged:${it}")
            FragmentContainerActivity.launch(this@LiveDataWrongUsageActivity,LiveDataDetailFragment::class.java,null)
        }
    }


    private fun byEventWrapper(){
        mBinding.btnEventWrapper.setOnClickListener {
            shareViewModel.goToDetailByEventWrapper("Hello")
        }
        shareViewModel.navigationToDetailEventWrapper.observe(this){
            val content1 = it.getContentIfNotHandled()
            val content2 = it.getContentIfNotHandled()
            println("LiveDataWrongUsageFragment useEventWrapper onChange content1:${content1}, content2:${content2}")
            content1?.let {
                FragmentContainerActivity.launch(this@LiveDataWrongUsageActivity,LiveDataDetailFragment::class.java,null)
            }
        }
    }

    private fun sleep(){
        Thread.sleep(3000)

    }


    companion object{
        @JvmStatic
        fun launch(activity: Activity){
            launchActivity<LiveDataWrongUsageActivity>(activity)
        }
    }

}