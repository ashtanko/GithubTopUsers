package dev.shtanko.common.android.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

fun Activity.dpToPixel(dp: Float): Int {
    val resources = this.resources
    val metrics = resources.displayMetrics
    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

}

val Activity.statusBarHeight: Int
    get() {
        val resId = this.resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android")

        return if (resId > 0) {
            this.resources.getDimensionPixelSize(resId)
        } else {
            dpToPixel(25f)
        }
    }

val Activity.isTranslucentStatusBar: Boolean
    @SuppressLint("ObsoleteSdkInt") //todo check
    get() {
        val w = this.window
        val lp = w.attributes
        val flags = lp.flags

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            false
        }
    }