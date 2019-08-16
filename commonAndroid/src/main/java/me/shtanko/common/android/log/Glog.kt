package me.shtanko.common.android.log

import android.util.Log
import java.util.*

object Glog {
    private const val TAG = "GITHUB_TOP_USERS_APP"

    fun d(tag: String = TAG, vararg args: Any) {
        val str = Arrays.toString(args)
        Log.d(tag, str)
    }
}