package com.tesla.framework.component.logger.k9

/**
 * Logging abstraction based on Timber.
 */
interface ILogger {
    fun v(message: String?, vararg args: Any?)
    fun v(t: Throwable?, message: String?, vararg args: Any?)
    fun v(t: Throwable?)

    fun d(message: String?, vararg args: Any?)
    fun d(t: Throwable?, message: String?, vararg args: Any?)
    fun d(t: Throwable?)

    fun i(message: String?, vararg args: Any?)
    fun i(t: Throwable?, message: String?, vararg args: Any?)
    fun i(t: Throwable?)

    fun w(message: String?, vararg args: Any?)
    fun w(t: Throwable?, message: String?, vararg args: Any?)
    fun w(t: Throwable?)

    fun e(message: String?, vararg args: Any?)
    fun e(t: Throwable?, message: String?, vararg args: Any?)
    fun e(t: Throwable?)
}
