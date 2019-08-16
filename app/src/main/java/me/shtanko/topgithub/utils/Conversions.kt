package me.shtanko.topgithub.utils

import kotlin.experimental.and
import kotlin.experimental.or


object Conversions {

    fun intToByteArray(value: Int): ByteArray {
        val bytes = ByteArray(4)
        intToByteArray(bytes, 0, value)
        return bytes
    }

    private fun intToByteArray(bytes: ByteArray, offset: Int, value: Int): Int {
        bytes[offset + 3] = value.toByte()
        bytes[offset + 2] = (value shr 8).toByte()
        bytes[offset + 1] = (value shr 16).toByte()
        bytes[offset] = (value shr 24).toByte()
        return 4
    }

    fun byteArrayToInt(bytes: ByteArray): Int {
        return byteArrayToInt(bytes, 0)
    }

    private fun byteArrayToInt(bytes: ByteArray, offset: Int): Int {
        return (bytes[offset] and (0xff shl 24).toByte() or
                (bytes[offset + 1] and (0xff shl 16).toByte()) or (
                bytes[offset + 2] and (0xff shl 8).toByte()) or
                (bytes[offset + 3] and 0xff.toByte())).toInt()
    }

}