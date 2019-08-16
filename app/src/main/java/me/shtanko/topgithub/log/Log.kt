package me.shtanko.topgithub.log

import me.shtanko.core.Logger


object Log {

    private const val LOG_TAG_MAX_CHARACTERS = 23

    private var loggers: Array<out Logger> = emptyArray()

    fun initialize(vararg loggers: Logger) {
        this.loggers = loggers
    }

    fun v(tag: String, message: String, throwable: Throwable?) {
        for (logger in loggers) {
            logger.v(tag, message, throwable)
        }
    }

    fun v(tag: String, message: String) {
        v(tag, message, null)
    }

    private fun tag(clazz: Class<*>): String {

        val simpleName = clazz.simpleName
        if (simpleName.length > LOG_TAG_MAX_CHARACTERS) {
            return simpleName.substring(0, LOG_TAG_MAX_CHARACTERS)
        }
        return simpleName
    }

}