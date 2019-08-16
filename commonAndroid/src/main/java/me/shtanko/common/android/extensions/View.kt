package me.shtanko.common.android.extensions

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.visibilityOnEdit(length: Int) {
    visibility = if (length == 0) {
        INVISIBLE
    } else {
        VISIBLE
    }
}