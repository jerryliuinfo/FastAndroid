package com.apache.fastandroid.demo.designmode.wrapper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * Created by Jerry on 2021/10/15.
 */
  class MyToast(val context: Context,val base: Toast): Toast(context) {


    companion object{
        fun makeText(context: Context?, text: CharSequence, duration: Int): MyToast {
            val toast = Toast.makeText(context, text, duration)
            return MyToast(context!!, toast)
        }
    }
}