package com.apache.fastandroid.demo.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.rxjava.map.ArticleToVideoMapper
import com.apache.fastandroid.network.model.FakeThing
import com.apache.fastandroid.network.model.FakeToken
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.Protocol
import com.blankj.utilcode.util.ToastUtils
import com.chad.baserecyclerviewadapterhelper.entity.Person
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava.*


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("扔物线", "扔物线",RxJavaDemoFragment::class.java),
            ViewItemBean("扔物线", "南尘",RxJavaDemoFragment2::class.java)
        )
    }

}