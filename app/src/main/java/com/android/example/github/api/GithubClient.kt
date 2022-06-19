package com.android.example.github.api

import com.apache.fastandroid.network.retrofit.RetrofitFactory

/**
 * Created by Jerry on 2022/5/15.
 */
object GithubClient {


    val githubService:GithubService =  RetrofitFactory.instance.create(
        GithubService::class.java,
        "https://api.github.com/"
    )
}