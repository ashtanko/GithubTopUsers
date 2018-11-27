package me.shtanko.topgithub.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import me.shtanko.topgithub.R
import me.shtanko.topgithub.platform.BaseActivity
import me.shtanko.topgithub.ui.main.MainFragment

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance())
                .commitNow()
        }
    }

    companion object {
        fun intent(context: Context) = Intent(context, DetailsActivity::class.java)
    }
}