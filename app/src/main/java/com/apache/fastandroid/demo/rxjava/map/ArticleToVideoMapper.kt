package com.apache.fastandroid.demo.rxjava.map

import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.result.BaseResponse
import io.reactivex.rxjava3.functions.Function

/**
 * Created by Jerry on 2021/9/9.
 */
class ArticleToVideoMapper:Function<BaseResponse<List<ArticleApi>>, List<UserBean>> {


    companion object{
        private val INSTANCE: ArticleToVideoMapper by lazy {
            ArticleToVideoMapper()
        }
        fun getInstance(): ArticleToVideoMapper {
            return INSTANCE
        }
    }

    override fun apply(t: BaseResponse<List<ArticleApi>>): List<UserBean> {
        val userList = ArrayList<UserBean>()
        var list = t.data
         for ((index, value) in list.withIndex()){
            if (index == 0){
                userList.add(UserBean(value.chapterName,value.primaryKeyId))
            }
        }
        return userList

    }
}