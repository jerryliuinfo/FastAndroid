package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apache.fastandroid.article.ArticleNetwork
import com.apache.fastandroid.databinding.FragmentCouritineBestPracticeBinding
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.jetpack.flow.vm.PostViewModel
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.response.BaseResponse
import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import com.apache.fastandroid.util.extensitons.runOnUi
import com.blankj.utilcode.util.ToastUtils
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.awaitNextLayout
import com.tesla.framework.ui.fragment.BaseVBFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineBestPracticeFragment :
    BaseVBFragment<FragmentCouritineBestPracticeBinding>(FragmentCouritineBestPracticeBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineDemoFragment"
    }

    private val mViewModel: PostViewModel by viewModels {
        getViewModelFactory()
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        /**
         * repeatOnLifecycle 是一个挂起函数，它以 Lifecycle.State 作为参数，用于在生命周期达到该状态时自动创建和启动一个新的协程，并将块传递给它，
         * 并在生命周期达到该状态时取消正在执行该块的正在进行的协程 低于状态。
         */
        lifecycleScope.launchWhenStarted {



        }

        /**
         * 使用数据流时，最好通过适当的协程作用域和 repeatOnLifecycle API 来处理这一任务：
         *
         */
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect{

                }
            }
        }
    }


}

