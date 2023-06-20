package com.apache.fastandroid.demo.designmode.adapter

import com.apache.fastandroid.demo.bean.TtsRole
import com.apache.fastandroid.demo.bean.TtsShowBean

/**
 * Created by Jerry on 2023/6/8.
 */
interface IDataAdapter<T> {

    fun adapter(t:T):List<TtsRole>

    fun mapToUI(t:T):List<TtsShowBean>
}