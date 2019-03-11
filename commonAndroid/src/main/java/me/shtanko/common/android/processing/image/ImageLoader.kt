package me.shtanko.common.android.processing.image

import android.widget.ImageView

interface ImageLoader {

    fun load(source: Any,
             transform: TransformType = TransformType.NONE,
             target: ImageView,
             placeholder: Int,
             error: Int,
             onLoadSucceed: (() -> Unit)? = null,
             onLoadFailed: (() -> Unit)? = null)

    fun load(url: String, transform: TransformType = TransformType.NONE,
             target: ImageView, placeholder: Int, error: Int)

    fun load(source: Any, transform: TransformType = TransformType.NONE,
             target: ImageView, placeholder: Int, error: Int)
}