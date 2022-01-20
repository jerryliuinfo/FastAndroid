package com.apache.fastandroid.demo.hawk.interfaces

import com.apache.fastandroid.demo.hawk.bean.DataInfo
import java.lang.Exception

/**
 * Created by Jerry on 2022/1/20.
 */
interface IConverter {

   fun <T> toString(value:T):String?

   @Throws(Exception::class)
   fun <T> fromString(value: String?, dataInfo: DataInfo?): T

}