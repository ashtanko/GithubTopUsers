package me.shtanko.topgithub.log

import androidx.annotation.NonNull
import me.shtanko.topgithub.utils.Conversions
import me.shtanko.topgithub.utils.GrowingBuffer
import java.io.*
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.*


object LogFile {

    class Writer(@NonNull val file: File) {


        private var cipher: Cipher

        private val ivBuffer = ByteArray(16)

        private val cipherTextBuffer = GrowingBuffer()

        private var outputStream: BufferedOutputStream = BufferedOutputStream(FileOutputStream(file, true))

        init {

            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            } catch (e: NoSuchAlgorithmException) {
                throw AssertionError(e)
            } catch (e: NoSuchPaddingException) {
                throw AssertionError(e)
            }
        }

        fun close() {
            try {
                outputStream.close()
            } catch (ignored: IOException) {

            }
        }

        @Throws(IOException::class)
        fun writeEntry(@NonNull entry: String) {
            SecureRandom().nextBytes(ivBuffer)

            val plaintext = entry.toByteArray()

            try {

                //cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(secret, "AES"), IvParameterSpec(ivBuffer))
                //var cipherLength = cipher.getOutputSize(plaintext.size)
                //val cipherText = cipherTextBuffer[cipherLength]
                //cipherLength = cipher.doFinal(plaintext, 0, plaintext.size, cipherText)

                outputStream.write(ivBuffer)
                outputStream.write(Conversions.intToByteArray(plaintext.size))
                outputStream.write(plaintext, 0, plaintext.size)
                outputStream.flush()

            } catch (e: ShortBufferException) {
                handle(e)
            } catch (e: InvalidAlgorithmParameterException) {
                handle(e)
            } catch (e: InvalidKeyException) {
                handle(e)
            } catch (e: BadPaddingException) {
                handle(e)
            } catch (e: IllegalBlockSizeException) {
                handle(e)
            }

        }

        private fun handle(e: Exception) = AssertionError(e)
    }

    class Reader(@NonNull val file: File) {

        private val inputStream = BufferedInputStream(FileInputStream(file))
        private val ivBuffer = ByteArray(16)
        private val intBuffer = ByteArray(4)


        @Throws(IOException::class)
        private fun readEntry(): String {
            return try {

                readFully(inputStream, ivBuffer)
                readFully(inputStream, intBuffer)

                val length = Conversions.byteArrayToInt(intBuffer)

                ""

            } catch (e: EOFException) {
                ""
            }
        }

        @Throws(IOException::class)
        private fun readFully(inputStream: InputStream, buffer: ByteArray) {
            readFully(inputStream, buffer, buffer.size)
        }

        @Throws(IOException::class)
        private fun readFully(inputStream: InputStream, buffer: ByteArray, len: Int) {
            var offset = 0
            while (true) {
                val read = inputStream.read(buffer, offset, len - offset)
                if (read == -1) throw EOFException("Stream ended early")

                if (read + offset < len)
                    offset += read
                else
                    return
            }
        }
    }

    fun grabLogcat(): String {
        return try {

            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val log = StringBuilder()

            val separator = System.getProperty("line.separator")

            while (true) {
                val line = bufferedReader.readLine() ?: break
                log.append(line)
                log.append(separator)
            }


            return log.toString()


        } catch (e: IOException) {
            ""
        }
    }

}