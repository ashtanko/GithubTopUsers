package me.shtanko.topgithub.ui.main.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isOnline(): Boolean {
    return try {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo.isConnected
    } catch (ex: Exception) {
        ex.printStackTrace()
        false
    }
}