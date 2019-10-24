package dev.shtanko.topgithub.utils

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

class ContentViewBindingDelegate<in R : Fragment, out T : ViewDataBinding>(
        @LayoutRes private val layoutRes: Int
) {

    private var binding: T? = null

    operator fun getValue(activity: R, property: KProperty<*>): T {
        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity.context), layoutRes, null, false)
        }
        return binding!!
    }
}

fun <R : Fragment, T : ViewDataBinding> contentView(
        @LayoutRes layoutRes: Int
): ContentViewBindingDelegate<R, T> = ContentViewBindingDelegate(layoutRes)
