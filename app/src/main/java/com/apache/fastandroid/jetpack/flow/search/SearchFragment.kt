package com.apache.fastandroid.jetpack.flow.search

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ActivitySearchBinding
import com.tesla.framework.kt.getQueryTextChangeStateFlow
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Jerry on 2022/5/5.
 */
class SearchFragment:BaseBindingFragment<ActivitySearchBinding>(ActivitySearchBinding::inflate),CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        job = Job()

        setupSearchView()

    }

    private fun setupSearchView() {
        launch {
            mBinding.searchView.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()){
                        mBinding.textViewResult.text = ""
                        return@filter false
                    }else{
                        return@filter true
                    }
                }.distinctUntilChanged()
                .flatMapLatest {
                    dataFromNetwork(it).catch {
                        emitAll(flowOf(""))
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    mBinding.textViewResult.text = it
                }
        }
    }

    private fun dataFromNetwork(query:String):Flow<String>{
        return flow {

            delay(3000)
            emit(query)
        }
    }

}