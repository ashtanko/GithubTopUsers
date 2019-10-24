package dev.shtanko.topgithub.extensions

import java.io.File

fun File.notExists(): Boolean {
    return exists().not()
}