package com.apache.fastandroid.jetpack.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import java.math.BigDecimal

/**
 * Created by Jerry on 2021/2/7.
 */
class StockLiveData(symbol:String): LiveData<BigDecimal>() {

    private var stockMananger = StockMananger(symbol)

    private val mListener:StockMananger.PriceListener = object :StockMananger.PriceListener{
        override fun onPriceUpdate(price: BigDecimal) {
            value = price
        }
    }

    override fun onActive() {
        super.onActive()
        stockMananger.requestPriceUpdates(mListener)
    }

    override fun onInactive() {
        super.onInactive()
        stockMananger.removeUpdates(mListener)

    }

    companion object{
        private const val TAG = "StockLiveData"
        private lateinit var sIntance:StockLiveData

        fun get(symbol:String):StockLiveData {
            sIntance = if (::sIntance.isInitialized) sIntance else StockLiveData(symbol)
            return sIntance
        }
    }
}