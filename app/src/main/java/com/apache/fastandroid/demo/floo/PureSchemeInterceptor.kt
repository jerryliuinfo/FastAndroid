package com.apache.fastandroid.demo.floo

import com.apache.fastandroid.BuildConfig
import me.drakeet.floo.Chain
import me.drakeet.floo.Interceptor

/**
 * Created by Jerry on 2022/6/29.
 */
class PureSchemeInterceptor(private val scheme: String) :
    Interceptor {
    override fun intercept(chain: Chain): Chain {
        var chain = chain
        if (BuildConfig.DEBUG && URLs.scheme() == chain.request().scheme) {
            chain = Chain(chain.request().buildUpon().scheme(scheme).build())
        }
        return chain
    }
}