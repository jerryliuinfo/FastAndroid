package com.apache.fastandroid.demo.designmode.adapter

import com.apache.fastandroid.demo.bean.ToneDto
import com.apache.fastandroid.demo.bean.TtsRole
import com.apache.fastandroid.demo.bean.TtsShowBean

/**
 * Created by Jerry on 2023/6/8.
 */
class ToneAdapter:IDataAdapter<ToneDto> {

    override fun adapter(t: ToneDto): List<TtsRole> {

        return emptyList()
    }

    override fun mapToUI(t: ToneDto): List<TtsShowBean> {
        return emptyList()
    }


}