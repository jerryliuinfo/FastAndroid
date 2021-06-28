package com.apache.fastandroid.demo.glide

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.glide_basic_usage_demo.image
import kotlinx.android.synthetic.main.glide_cache_usage_demo.*

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideCacheUsageFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.glide_cache_usage_demo
    }
    val url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2?token=99988882999"

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        btn_skip_memory.setOnClickListener {
            Glide.with(activity).load(url)
                    .placeholder(R.drawable.sample_footer_loading)
                    .skipMemoryCache(true)
                    .into(image)
        }
        btn_custom_url.setOnClickListener {
            Glide.with(activity).load(MyGlideUrl(url))
                    .placeholder(R.drawable.sample_footer_loading)

                    .into(image)
        }
        btn_global_custom_url.setOnClickListener {
            Glide.with(activity).load(url)
                    .placeholder(R.drawable.sample_footer_loading)
                    .into(image)
        }



    }


    private class MyGlideUrl(val url:String): GlideUrl(url) {
        override fun getCacheKey(): String {
            return url.replace(findTokenParam(url), "")
        }

        private fun findTokenParam(url: String): String {
            var tokenParam = ""
            val tokenKeyIndex = if (url.indexOf("?token=") >= 0) url.indexOf("?token=") else url.indexOf("&token=")
            if (tokenKeyIndex != -1) {
                val nextAndIndex = url.indexOf("&", tokenKeyIndex + 1)
                tokenParam = if (nextAndIndex != -1) {
                    url.substring(tokenKeyIndex + 1, nextAndIndex + 1)
                } else {
                    url.substring(tokenKeyIndex)
                }
            }
            return tokenParam
        }
    }
}