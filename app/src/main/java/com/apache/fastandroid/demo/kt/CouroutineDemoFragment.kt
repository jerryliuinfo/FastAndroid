package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.home.HomeReporsitory
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.fragment.BaseLifecycleFragment
import kotlinx.android.synthetic.main.fragment_kotlin_couritine.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2021/10/28.
 */
class CouroutineDemoFragment:BaseLifecycleFragment<FragmentKotlinCouritineBinding>() {
    companion object{
        private const val TAG = "CouroutineDemoFragment"
    }
    private lateinit var coroutineVewModel: CoroutineVewModel
    override fun inflateContentView(): Int {
        return R.layout.fragment_kotlin_couritine
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_suspend.setOnClickListener {
            loadData()
        }
        btn_view_model.setOnClickListener {
            coroutineVewModel.loadByViewModel()
        }
    }


    private   fun loadData(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = loadHomeArticle()
            NLog.d(TAG, "loadHomeArticle222 result: %s,  thread: %s",result,Thread.currentThread().name)

        }
    }

    private suspend fun loadHomeArticle() {
        withContext(Dispatchers.IO){
            NLog.d(TAG, "loadHomeArticle111 thread: %s",Thread.currentThread().name)
            var loadHomeArticleCoSync = HomeReporsitory.newInstance().loadHomeArticleCoSync(1)
        }
    }

    override fun initViewModel() {
        coroutineVewModel = getFragmentScopeViewModel(CoroutineVewModel::class.java)
    }
}