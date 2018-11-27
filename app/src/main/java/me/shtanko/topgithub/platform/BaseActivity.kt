package me.shtanko.topgithub.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.shtanko.topgithub.di.provideInjection

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        provideInjection()
        super.onCreate(savedInstanceState)
    }

}