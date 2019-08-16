package me.shtanko.topgithub.utils

class GrowingBuffer {

    private var buffer: ByteArray? = null

    operator fun get(minLength: Int): ByteArray? {

        buffer?.let {
            if (it.size < minLength) {
                buffer = ByteArray(minLength)
            }
        }

        return buffer
    }
}
