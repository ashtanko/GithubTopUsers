package dev.shtanko.core


abstract interface Logger {
    abstract fun v(tag: String, message: String, throwable: Throwable?)
    abstract fun d(tag: String, message: String, throwable: Throwable)
    abstract fun i(tag: String, message: String, throwable: Throwable)
    abstract fun w(tag: String, message: String, throwable: Throwable)
    abstract fun e(tag: String, message: String, throwable: Throwable)
    abstract fun wtf(tag: String, message: String, throwable: Throwable)
}