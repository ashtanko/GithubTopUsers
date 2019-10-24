package dev.shtanko.topgithub.log

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dev.shtanko.core.Logger
import dev.shtanko.topgithub.exception.NoExternalStorageException
import dev.shtanko.topgithub.extensions.notExists
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class PersistentLogger(private val context: Context) : Logger {

    companion object {
        @SuppressLint("SimpleDateFormat")
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz")
        private const val MAX_LOG_FILES = 5
        private const val BYTE = 1024
        private const val KB = 1024 * BYTE
        private const val MAX_LOG_SIZE = 300 * KB
        private const val LOG_DIRECTORY = "log"
        private const val FILENAME_PREFIX = "log-"
        private val TAG = PersistentLogger::class.java.simpleName

        private const val LOG_V = "V"
        private const val LOG_D = "D"
        private const val LOG_I = "I"
        private const val LOG_W = "W"
        private const val LOG_E = "E"
        private const val LOG_WTF = "A"
    }

    private val executor: Executor by lazy {
        Executors.newSingleThreadExecutor { r ->
            val thread = Thread(r, "logger")
            thread.priority = Thread.MIN_PRIORITY
            thread
        }
    }

    private lateinit var writer: LogFile.Writer

    init {
        executor.execute(::initializeWriter)
    }


    override fun v(tag: String, message: String, throwable: Throwable?) {
        write(LOG_V, tag, message, throwable)
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        write(LOG_D, tag, message, throwable)
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        write(LOG_I, tag, message, throwable)
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        write(LOG_W, tag, message, throwable)
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        write(LOG_E, tag, message, throwable)
    }

    override fun wtf(tag: String, message: String, throwable: Throwable) {
        write(LOG_WTF, tag, message, throwable)
    }


    private fun initializeWriter() {
        try {
            writer = LogFile.Writer(getOrCreateActiveLogFile())
        } catch (e: NoExternalStorageException) {
            android.util.Log.e(TAG, "Failed to initialize writer.", e)
        } catch (e: IOException) {
            android.util.Log.e(TAG, "Failed to initialize writer.", e)
        }

    }

    private fun write(level: String, tag: String, message: String, throwable: Throwable?) {

        executor.execute {


            try {

                if (::writer.isInitialized.not()) {
                    return@execute
                }

                if (writer.file.length() >= MAX_LOG_SIZE) {
                    writer.close()
                    writer = LogFile.Writer(createNewLogFile())
                    trimLogFilesOverMax()
                }

                for (entry in buildLogEntries(level, tag, message, throwable)) {
                    writer.writeEntry(entry)
                }

            } catch (e: NoExternalStorageException) {
                android.util.Log.w(TAG, "Cannot persist logs.", e)
            } catch (e: IOException) {
                android.util.Log.w(TAG, "Failed to write line. Deleting all logs and starting over.")
                deleteAllLogs()
                initializeWriter()
            }
        }
    }

    @Throws(NoExternalStorageException::class)
    private fun trimLogFilesOverMax() {
        val logs = getSortedLogFiles()
        if (logs.size > MAX_LOG_FILES) {
            for (i in MAX_LOG_FILES until logs.size) {
                logs[i].delete()
            }
        }
    }

    private fun deleteAllLogs() {
        try {
            val logs = getSortedLogFiles()
            for (log in logs) {
                log.delete()
            }
        } catch (e: NoExternalStorageException) {
            Log.w(TAG, "Was unable to delete logs.", e)
        }
    }

    @Throws(NoExternalStorageException::class)
    private fun getOrCreateActiveLogFile(): File {
        val logs = getSortedLogFiles()
        if (logs.isNotEmpty()) return logs[0]
        return createNewLogFile()
    }

    @Throws(NoExternalStorageException::class)
    private fun createNewLogFile(): File {
        return File(getOrCreateLogDirectory(), "$FILENAME_PREFIX${System.currentTimeMillis()}")
    }

    @Throws(NoExternalStorageException::class)
    private fun getSortedLogFiles(): Array<File> {
        val logs = getOrCreateLogDirectory().listFiles()
        Arrays.sort(logs) { o1, o2 -> o2.name.compareTo(o1.name) }
        return logs
    }

    @Throws(NoExternalStorageException::class)
    private fun getOrCreateLogDirectory(): File {
        val logDir = File(context.cacheDir, LOG_DIRECTORY)
        if (logDir.notExists() && logDir.mkdir().not()) {
            throw NoExternalStorageException("Unable to create log directory.")
        }
        return logDir
    }

    private fun buildLogEntries(level: String, tag: String, message: String, throwable: Throwable?): List<String> {
        val entries = mutableListOf<String>()
        val date = Date()

        entries.add(buildEntry(level, tag, message, date))

        throwable?.let { t ->
            val outputStream = ByteArrayOutputStream()
            t.printStackTrace(PrintStream(outputStream))
            val trace = outputStream.toByteArray().toString()
            val lines = trace.split("\\n".toRegex())
            for (line in lines) {
                entries.add(buildEntry(level, tag, line, date))
            }
        }

        return entries
    }

    private fun buildEntry(level: String, tag: String, message: String, date: Date): String {
        return "${DATE_FORMAT.format(date)} $level $tag $message"
    }

}