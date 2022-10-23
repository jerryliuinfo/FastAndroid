package com.apache.fastandroid.util

import com.tesla.framework.component.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jerry on 2022/2/7.
 */
object DateUtil {
    val nowDateTime:String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return sdf.format(Date())
        }


    val nowDate:String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.format(Date())
        }
    val nowTime:String
        get() {
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(Date())
        }

    var nowTimeHor:String = ""

}