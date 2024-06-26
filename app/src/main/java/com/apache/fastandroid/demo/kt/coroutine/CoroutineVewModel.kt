package com.apache.fastandroid.demo.kt.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.calladapter.networkresult.onFailure
import com.apache.fastandroid.network.calladapter.networkresult.onSuccess
import com.apache.fastandroid.network.calladapter.networkresult.runCatching
import com.apache.fastandroid.network.model.result.BaseResponse
import com.apache.fastandroid.network.model.result.EmptyResponse
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.blankj.utilcode.util.NetworkUtils
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resumeWithException

/**
 * Created by Jerry on 2021/10/28.
 */
class CoroutineVewModel : ViewModel() {
    companion object {
        private const val TAG = "CoroutineVewModel"
    }

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    //将可修改的 MutableStateFlow 转换成 只读的 StateFlow
    val uiState = _uiState.asStateFlow()

    data class NewsUiState(
        val userMessages:List<News> = listOf(),
        val isLoading: Boolean = false,
        val userMessage:String? = null
    )

    data class News(val id: Long, val message: String)

    fun refreshNews(){
        viewModelScope.launch {
            if (!NetworkUtils.isConnected()){
                _uiState.update {  currentUiState ->
                    currentUiState.copy(userMessage = "No Internet connection")
                }

                return@launch
            }
            //do something else
        }
    }

    fun userMessageShown() {
        _uiState.update { currentUiState ->
            currentUiState.copy(userMessage = null)
        }
    }

    fun load(){
        viewModelScope.launch {
            runCatching {
                getArticleById(100)
            }
        }
    }




    suspend fun getUserInfoById(name: String): UserBean {
        return withContext(Dispatchers.IO) {
            delay(1000)
            UserBean(name, 18)
        }
    }

    suspend fun callbackToSuspend():BaseResponse<EmptyResponse> =
        suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                //协程被取消时
            }

            RetrofitFactory.get().apiService().collect(1000).enqueue(object : Callback<BaseResponse<EmptyResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<EmptyResponse>>,
                    response: Response<BaseResponse<EmptyResponse>>
                ) {

                    response.takeIf { it.isSuccessful }?.body()
                        ?.let { continuation.resumeWith(Result.success(it)) }
                        ?: continuation.resumeWithException(HttpException(response))
                }

                override fun onFailure(call: Call<BaseResponse<EmptyResponse>>, t: Throwable) {
                    continuation.resumeWithException(t)

                }

            })
        }

    private val reporsitoryKt =
        HomeReporsitoryKt(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())

    fun coroutineScopeRequest() {
        CoroutineScopeUtil.coroutineScopeRequest()
    }


    fun getArticleById(id: Long) {
        val result = try {
            Result.success(RetrofitFactory.get().apiService().collect(21613))
        } catch (ex: Exception) {
            Result.failure(Throwable(ex.message))
        }
        println("result:${result} thread:${Thread.currentThread().name}")

    }

    fun getArticleByIdWithNetworkResult() {
        viewModelScope.launch {
            RetrofitFactory.get().apiService().getArticleByIdWithNetworkResult(21613).onSuccess {
                Logger.d("onSuccess result:${it}")
            }.onFailure {
                Logger.d("onFailure:${it.message}")

            }
        }

    }



    fun getArticleByIdWithRunCatching(id: Long) {
        val result = kotlin.runCatching {
            RetrofitFactory.get().apiService().collect(21613)
        }
        println("result2:${result} thread:${Thread.currentThread().name}")

    }

    fun testJob() {
        viewModelScope.launch {
            val job = launch() {
                delay(1000)
                println("testJob word thread:${Thread.currentThread().name}")
            }
            println("testJob Hello thread:${Thread.currentThread().name}")
            job.join()
            println("testJob done")
        }
    }


}