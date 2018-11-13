package me.shtanko.topgithub.ui.main.extensions

import android.support.v4.app.Fragment
import android.widget.Toast

fun Fragment.shortToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}