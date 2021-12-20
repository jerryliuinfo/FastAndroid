package com.apache.fastandroid.jetpack.livedata

import com.apache.fastandroid.util.extensitons.runOnUi
import com.tesla.framework.common.util.log.NLog
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.atomic.AtomicBoolean

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
            runOnUi { this }

        }
    }



    interface PriceListener{
        fun onPriceUpdate(price:BigDecimal)
    }

    private fun notifyPriceChanged(){
        NLog.d(TAG, "notifyPriceChanged mListeners size: %s", mListeners.size)
        mListeners.forEach {
            it.onPriceUpdate(java.util.Random().nextDouble().toBigDecimal().setScale(2, RoundingMode.HALF_UP))
        }
    }

    companion object{
        private const val TAG = "StockMananger"
    }

    override fun run() {
        notifyPriceChanged()
        if (mLoadData.get()){

//            runOnUIDelay(this,2000)


        }
    }
}