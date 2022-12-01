package com.apache.fastandroid.jetpack.relearnandroid

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.DatabindingComBindAdapterBinding
import com.apache.fastandroid.demo.designmode.DesignModeDemoFragment
import com.apache.fastandroid.jetpack.relearnandroid.vm.CommonBindAdapterViewModel
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseDataBindingFragment

/**
 * Created by Jerry on 2021/9/8.
 * ObservableField 和 LiveData 都可以在databinding中使用，那它俩选择哪个比较好呢？
 * 其实它俩各有各的特点，ObservableField 的特点是支持防抖，在 set 的数据不变的情况下，
 * 可以避免不必要的重复绘制。LiveData 则没有防抖的特性。所以可以根据场合来选择合适的方案。
 */
class CommonBindingAdapterDemoFragment:BaseDataBindingFragment<DatabindingComBindAdapterBinding>(DatabindingComBindAdapterBinding::inflate) {
    companion object{
        private const val URL = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2"
        const val TAG = ""
    }

    private lateinit var viewModel: CommonBindAdapterViewModel
    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(CommonBindAdapterViewModel::class.java)
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnTestVisible.setOnClickListener {
            viewModel.loading.set(true)
            Handler().postDelayed({
                viewModel.loading.set(false)
            },1000)
        }

    }


    //内部类
    inner class ClickProxy{
        fun toMain(){
            NLog.d(TAG, "ClickProxy toMain")
            //默认持有外部类的引用，直接访问外部类的方法属性
            var activity = this@CommonBindingAdapterDemoFragment.activity
            if (activity != null) {
                FragmentContainerActivity.launch(activity, DesignModeDemoFragment::class.java, null)
            }
        }

        fun clickTestVisible(){
            viewModel.loading.set(true)
            Handler().postDelayed({
                viewModel.loading.set(false)
            },1000)
        }

        fun clickTestSelect(){
            viewModel.toggle()
        }

        fun clickTestLiveData(){
            viewModel.toggleLivedata()
        }
    }

}