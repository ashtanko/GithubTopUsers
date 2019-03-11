package me.shtanko.topgithub.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import me.shtanko.topgithub.R
import me.shtanko.topgithub.navigation.Navigator.Companion.EXTRA_USERNAME
import me.shtanko.topgithub.platform.BaseActivity
import me.shtanko.topgithub.ui.main.MainFragment

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_activity)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        title = username

        if (savedInstanceState == null) {
            navigator.openDetailsFragment(this, username)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun intent(context: Context) = Intent(context, DetailsActivity::class.java)
    }
}