package com.tesla.framework.component.logger.k9

/**
 * A [Logger] implementation that does nothing.
 */
class NoOpLogger : ILogger {
    override fun v(message: String?, vararg args: Any?) = Unit

    override fun v(t: Throwable?, message: String?, vararg args: Any?) = Unit

    override fun v(t: Throwable?) = Unit

    override fun d(message: String?, vararg args: Any?) = Unit

    override fun d(t: Throwable?, message: String?, vararg args: Any?) = Unit

    override fun d(t: Throwable?) = Unit

    override fun i(message: String?, vararg args: Any?) = Unit

    override fun i(t: Throwable?, message: String?, vararg args: Any?) = Unit

    override fun i(t: Throwable?) = Unit

    override fun w(message: String?, vararg args: Any?) = Unit

    override fun w(t: Throwable?, message: String?, vararg args: Any?) = Unit

    override fun w(t: Throwable?) = Unit

    override fun e(message: String?, vararg args: Any?) = Unit

    override fun e(t: Throwable?, message: String?, vararg args: Any?) = Unit

    override fun e(t: Throwable?) = Unit
}
