package me.shtanko.topgithub.log

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.shtanko.topgithub.R


class SubmitLogActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_log)

        val logPreviewAdapter = LogPreviewAdapter()

        val logPreview = findViewById<RecyclerView>(R.id.log_preview)
        logPreview.layoutManager = LinearLayoutManager(this)
        logPreview.adapter = logPreviewAdapter


        val logcat = LogFile.grabLogcat()
        logPreviewAdapter.setText(logcat)

    }

}