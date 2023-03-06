package com.apache.fastandroid.demo.designmode.builder



class StartupConfig private constructor(
    val awaitTimeout: Long,
    val openStatistic: Boolean? = true
) {

    class Builder {
        private var mAwaitTimeout: Long? = null
        private var mOpenStatistics: Boolean? = true

        companion object {
            const val AWAIT_TIMEOUT = 10000L
        }


        fun setAwaitTimeout(timeoutMilliSeconds: Long):Builder = apply {
            mAwaitTimeout = timeoutMilliSeconds
        }



        fun setOpenStatistics(openStatistic: Boolean):Builder = apply {
            mOpenStatistics = openStatistic
        }

        fun build(): StartupConfig {
            return StartupConfig(
                mAwaitTimeout ?: AWAIT_TIMEOUT,
                mOpenStatistics
            )
        }
    }

}