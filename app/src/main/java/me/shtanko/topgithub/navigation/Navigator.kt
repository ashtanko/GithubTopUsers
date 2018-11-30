package me.shtanko.topgithub.navigation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import me.shtanko.topgithub.R
import me.shtanko.topgithub.platform.BaseActivity
import me.shtanko.topgithub.ui.details.DetailsActivity
import me.shtanko.topgithub.ui.details.DetailsFragment
import me.shtanko.topgithub.ui.main.MainFragment
import javax.inject.Inject

class Navigator @Inject constructor() {

    companion object {
        private val EXTRA_USER_ID = "${DetailsActivity::class.java.`package`.name}.extra.USER_ID"
    }


    fun openMainFragment(activity: AppCompatActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    fun openDetailsFragment(activity: AppCompatActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance())
            .commitNow()
    }

    fun openDetailsActivity(activity: Activity, event: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(EXTRA_USER_ID, event)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()
        ActivityCompat.startActivity(activity, intent, options)

    }
}