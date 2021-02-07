package com.apache.fastandroid.jetpack.livedata

import android.os.Looper
import androidx.core.os.postDelayed
import com.tesla.framework.common.util.handler.HandlerUtil
import com.tesla.framework.common.util.log.NLog
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean
import java.util.logging.Handler
import kotlin.random.Random

/**
 * Created by Jerry on 2021/2/7.
 */
class StockMananger(symbol:String) : Runnable {

    private val mListeners= mutableListOf<PriceListener>()

    private val mLoadData:AtomicBoolean = AtomicBoolean(false)


    fun requestPriceUpdates(listener: PriceListener){
        if (!mListeners.contains(listener)){
            mListeners.add(listener)
        }
        loadStockData()
    }

    fun removeUpdates(listener: PriceListener){
        if (mListeners.contains(listener)){
            mListeners.remove(listener)
        }
        if (mListeners.size == 0){
            mLoadData.compareAndSet(true,false)
        }
    }

    private fun loadStockData(){
        if (mLoadData.compareAndSet(false,true)){
            HandlerUtil.getUIHandler().post(this)
        }
    }



    interface PriceListener{
        fun onPriceUpdate(price:BigDecimal)
    }

    private fun notifyPriceChanged(){
        NLog.d(TAG, "notifyPriceChanged mListeners size: %s", mListeners.size)
        mListeners.forEach {
            it.onPriceUpdate(java.util.Random().nextDouble().toBigDecimal())
        }
    }

    companion object{
        private const val TAG = "StockMananger"
    }

    override fun run() {
        notifyPriceChanged()
        if (mLoadData.get()){
            HandlerUtil.getUIHandler().postDelayed(this, 2000)
        }
    }
}