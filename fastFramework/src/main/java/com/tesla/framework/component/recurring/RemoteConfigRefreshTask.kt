package com.tesla.framework.component.recurring

import java.util.Date
import java.util.concurrent.TimeUnit

class RemoteConfigRefreshTask : RecurringTask() {
    override val name = "remote-config-refresher"

    override fun shouldRun(lastRun: Date): Boolean {
        return System.currentTimeMillis() - lastRun.time >= RUN_INTERVAL_MILLI
    }

    override fun run(lastRun: Date, callback: (result: Boolean) -> Unit) {

        callback.invoke(true)
    }



    companion object {
        private val RUN_INTERVAL_MILLI = TimeUnit.DAYS.toMillis(1)
    }
}
