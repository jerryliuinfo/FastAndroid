package com.apache.fastandroid.demo.logger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.orhanobut.logger.*
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import java.util.*


/**
 * Description:TODO
 * Create Time:2021/12/19 12:10 上午
 * Author:jerry
 *
 */

class LoggerDemoFragment:BaseStatusFragmentNew() {
 override fun inflateContentView(): Int {
  return R.layout.fragment_common
 }

 override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
  super.layoutInit(inflater, savedInstanceState)

  Log.d("Tag", "I'm a log which you don't see easily, hehe")
  Log.d("json content", "{ \"key\": 3, \n \"value\": something}")
  Log.d("error", "There is a crash somewhere or any warning")

  Logger.addLogAdapter(AndroidLogAdapter())
  Logger.d("message")

  Logger.clearLogAdapters()


  var formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
   .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
   .methodCount(0) // (Optional) How many method line to show. Default 2
   .methodOffset(3) // (Optional) Skips some method invokes in stack trace. Default 5
   //        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
   .tag("My custom tag") // (Optional) Custom tag for each log. Default PRETTY_LOGGER
   .build()

  Logger.addLogAdapter(AndroidLogAdapter(formatStrategy!!))

  Logger.addLogAdapter(object : AndroidLogAdapter() {
   override fun isLoggable(priority: Int, tag: String?): Boolean {
    return BuildConfig.DEBUG
   }
  })

  Logger.addLogAdapter(DiskLogAdapter())


  Logger.w("no thread info and only 1 method")

  Logger.clearLogAdapters()
  formatStrategy = PrettyFormatStrategy.newBuilder()
   .showThreadInfo(false)
   .methodCount(0)
   .build()

  Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
  Logger.i("no thread info and method info")

  Logger.t("tag").e("Custom tag for only one use")

  Logger.json("{ \"key\": 3, \"value\": something}")

  Logger.d(Arrays.asList("foo", "bar"))

  val map: MutableMap<String, String> = HashMap()
  map["key"] = "value"
  map["key1"] = "value2"

  Logger.d(map)

  Logger.clearLogAdapters()
  formatStrategy = PrettyFormatStrategy.newBuilder()
   .showThreadInfo(false)
   .methodCount(0)
   .tag("MyTag")
   .build()
  Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

  Logger.w("my log message with my tag")
 }
}