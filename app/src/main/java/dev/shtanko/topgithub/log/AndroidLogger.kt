package dev.shtanko.topgithub.log

import dev.shtanko.core.Logger


class AndroidLogger : Logger {
    override fun v(tag: String, message: String, throwable: Throwable?) {
        android.util.Log.v(tag, message, throwable)
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        android.util.Log.d(tag, message, throwable)
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        android.util.Log.i(tag, message, throwable)
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        android.util.Log.w(tag, message, throwable)
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        android.util.Log.e(tag, message, throwable)
    }

    override fun wtf(tag: String, message: String, throwable: Throwable) {
        android.util.Log.wtf(tag, message, throwable)
    }
}