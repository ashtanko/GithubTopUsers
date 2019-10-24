package dev.shtanko.common.android

import android.content.Context
import dev.shtanko.common.android.extensions.isOnline

class NetworkHandler
constructor(private val context: Context) {
    val isConnected get() = context.isOnline()
}