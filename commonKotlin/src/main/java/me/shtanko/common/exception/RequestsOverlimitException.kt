package me.shtanko.common.exception

class RequestsOverlimitException(override val message: String?) : Exception(message)