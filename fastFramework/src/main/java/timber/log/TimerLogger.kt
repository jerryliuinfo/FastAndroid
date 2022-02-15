package timber.log

import android.content.Context
import android.util.Log
import com.tesla.framework.component.logger.LogAdapter
import com.tesla.framework.BuildConfig
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

/**
 * Created by Jerry on 2022/1/19.
 */
class TimerLogger: LogAdapter {
    override fun doInit(context: Context?) {
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        } else {
            plant(CrashReportingTree())
        }
    }

    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return false
    }

    override fun log(priority: Int, tag: String?, message: String) {
        println("TimerLogger log message:${message}")
        Timber.log(priority,message)
    }

}