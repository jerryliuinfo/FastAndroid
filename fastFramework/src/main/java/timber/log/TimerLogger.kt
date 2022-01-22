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

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            FakeCrashLibrary.log(priority, tag, message)
            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }
}