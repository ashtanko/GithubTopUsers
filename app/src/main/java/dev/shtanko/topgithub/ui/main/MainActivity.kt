package dev.shtanko.topgithub.ui.main

import android.os.Bundle
import com.google.firebase.perf.metrics.AddTrace
import dev.shtanko.topgithub.R
import dev.shtanko.topgithub.platform.BaseActivity

@AddTrace(name = "onCreateTrace", enabled = true /* optional */)
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_activity)
        if (savedInstanceState == null) {
            navigator.openMainFragment(this)
        }
    }
}
