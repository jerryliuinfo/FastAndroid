package com.apache.fastandroid.demo.rxjava.map

import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.response.BaseResponse

/**
 * Created by Jerry on 2021/9/9.
 */
class ArticleToVideoMapper:io.reactivex.rxjava3.functions.Function<BaseResponse<List<Article>>, List<UserBean>> {


    companion object{
        private val INSTANCE: ArticleToVideoMapper by lazy {
            ArticleToVideoMapper()
        }
        fun getInstance(): ArticleToVideoMapper {
            return INSTANCE
        }
    }

    override fun apply(t: BaseResponse<List<Article>>): List<UserBean> {
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