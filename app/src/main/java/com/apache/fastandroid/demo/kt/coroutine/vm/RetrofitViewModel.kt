package com.apache.fastandroid.demo.kt.coroutine.vm

import android.view.View
import androidx.lifecycle.*
import com.apache.fastandroid.demo.repository.AppRepository
import com.apache.fastandroid.demo.repository.FollowersRepository
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.apache.fastandroid.network.model.Status
import com.apache.fastandroid.network.retrofit.ApiServiceFactory
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/5/16.
 */
class RetrofitViewModel(private val repository:AppRepository):ViewModel() {

    fun getUsers():LiveData<Resource<List<ApiUser>>>  = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        }catch (e:Exception){
            emit(Resource.error(msg = e.message ?: "error occurred", data = null))
        }
    }

    private val mFollowersRepository by lazy {
        FollowersRepository(ApiHelperImpl(ApiServiceFactory.flowService), viewModelScope)
    }

    private val _mApiUserListLiveData = MutableLiveData<Resource<List<ApiUser>>>()

    val mApiUserListLiveData:LiveData<Resource<List<ApiUser>>> = _mApiUserListLiveData

    fun listRepo(){
        Logger.d("listRepo data:")
        _mApiUserListLiveData.postValue(Resource.loading())
        mFollowersRepository.getUsers2 {
           Logger.d("listRepo data:$it")
            _mApiUserListLiveData.postValue(it)
        }
    }




    private val compositeDisposable = CompositeDisposable()
    private val _users = MutableLiveData<Resource<List<ApiUser>>>()


    fun getUsersByRxJava(){
        _users.postValue(Resource.loading(null))
        compositeDisposable.add(repository.getUsersSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userList:List<ApiUser> ->
                _users.postValue(Resource.success(userList))
            },{
                _users.postValue(Resource.error(it.message ?:"load error",null))
            })

        )
    }

    fun getUsersWithSingle():LiveData<Resource<List<ApiUser>>> = _users



}