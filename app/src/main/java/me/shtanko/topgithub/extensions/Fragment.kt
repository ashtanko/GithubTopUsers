package me.shtanko.topgithub.extensions

import android.widget.Toast

fun androidx.fragment.app.Fragment.shortToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}