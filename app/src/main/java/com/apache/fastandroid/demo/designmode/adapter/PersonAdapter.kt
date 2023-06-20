package com.apache.fastandroid.demo.designmode.adapter

import com.apache.fastandroid.demo.bean.PersonDto
import com.apache.fastandroid.demo.bean.TtsRole
import com.apache.fastandroid.demo.bean.TtsShowBean

/**
 * Created by Jerry on 2023/6/8.
 */
class PersonAdapter:IDataAdapter<PersonDto> {

    override fun adapter(t: PersonDto): List<TtsRole> {

        return emptyList()
    }

    override fun mapToUI(t: PersonDto): List<TtsShowBean> {

        return emptyList()
    }


}