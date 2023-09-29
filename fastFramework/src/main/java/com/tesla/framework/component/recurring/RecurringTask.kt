package com.tesla.framework.component.recurring

import com.tesla.framework.common.util.Prefs
import com.tesla.framework.component.logger.Logger
import java.util.*


abstract class RecurringTask {
    fun runIfNecessary() {
        val lastRunDate = lastRunDate
        val lastExecutionLog = "$name. Last execution was $lastRunDate."
        if (shouldRun(lastRunDate)) {
            Logger.d("Executing recurring task, $lastExecutionLog")
            run(lastRunDate){
                Prefs.setLastRunTime(name, absoluteTime)
            }
        } else {
            Logger.d("Skipping recurring task, $lastExecutionLog")
        }
    }

    protected abstract fun shouldRun(lastRun: Date): Boolean
    protected abstract fun run(lastRun: Date,callback:(result:Boolean) -> Unit)

    protected abstract val name: String

    protected val absoluteTime: Long
        get() = System.currentTimeMillis()
    private val lastRunDate: Date
        get() = Date(Prefs.getLastRunTime(name))
}
