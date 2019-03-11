package me.shtanko.common.android

import android.content.Context
import me.shtanko.common.android.extensions.isOnline
import javax.inject.Inject

class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.isOnline()
}