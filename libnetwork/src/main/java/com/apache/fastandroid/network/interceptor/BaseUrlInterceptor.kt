package com.apache.fastandroid.network.interceptor

import com.tesla.framework.component.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request

const val HEADER_BASE_URL = "Base-Url"

const val REPO_INFO = "Repo-Info"
const val OWNER = "Owner"
const val REPO = "Repo"

const val GITHUB_API_REPO_INFO = "https://api.github.com/repos/%s/%s"

class BaseUrlInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
    val originalRequest = chain.request()
    val builder: Request.Builder = originalRequest.newBuilder()
    val headers: List<String> = originalRequest.headers(HEADER_BASE_URL)
    if (headers.isNotEmpty()) {
      if (REPO_INFO == headers[0]) {
        val owner =
          originalRequest.headers(OWNER).getOrNull(0) ?: return chain.proceed(builder.build())
        val repo =
          originalRequest.headers(REPO).getOrNull(0) ?: return chain.proceed(builder.build())
        Logger.d("BaseUrlInterceptor: %s/%s", owner, repo)
        builder.removeHeader(HEADER_BASE_URL)
        builder.removeHeader(OWNER)
        builder.removeHeader(REPO)
        builder.url(String.format(GITHUB_API_REPO_INFO, owner, repo))
      }
    }
    return chain.proceed(builder.build())
  }
}
