package dev.shtanko.topgithub.image

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import dev.shtanko.common.android.processing.image.ImageLoader
import dev.shtanko.common.android.processing.image.TransformType
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {

    override fun load(
        source: Any,
        transform: TransformType,
        target: ImageView,
        placeholder: Int,
        error: Int,
        onLoadSucceed: (() -> Unit)?,
        onLoadFailed: (() -> Unit)?
    ) {

        GlideApp.with(target).load(getSource(source)).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {

                onLoadFailed?.invoke()

                return false
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any?, target: Target<Drawable>?,
                dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {

                onLoadSucceed?.invoke()

                return false
            }

        }).apply(getCropOptions(transform, placeholder, error)).into(target)

    }

    override fun load(source: Any, transform: TransformType, target: ImageView, placeholder: Int, error: Int) {
        val sourceToLoad: Comparable<*> = getSource(source)

        GlideApp.with(target).load(sourceToLoad).apply(getCropOptions(transform, placeholder, error)).into(target)
    }

    override fun load(url: String, transform: TransformType, target: ImageView, placeholder: Int, error: Int) {


        GlideApp.with(target).load(url).apply(getCropOptions(transform, placeholder, error)).into(target)
    }

    private fun getSource(data: Any): Comparable<*> {
        return when (data) {
            is String -> data
            is Uri -> data
            is Int -> data
            else -> {
                "" //todo
            }
        }
    }

    private fun getCropOptions(transform: TransformType, placeholder: Int, error: Int): RequestOptions {
        val baseOptions = getOptions(placeholder, error)
        return when (transform) {
            TransformType.CIRCLE_CROP -> {
                RequestOptions().circleCrop().apply(baseOptions)
            }
            TransformType.CENTER_CROP -> {
                RequestOptions().centerCrop().apply(baseOptions)
            }
            TransformType.FIT_CENTER -> {
                RequestOptions().fitCenter().apply(baseOptions)
            }
            TransformType.NONE -> {
                RequestOptions().fitCenter().apply(baseOptions)
            }
        }
    }

    private fun getOptions(placeholder: Int, error: Int): RequestOptions {
        return RequestOptions()
            .placeholder(placeholder)
            .error(error)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .priority(Priority.IMMEDIATE)
    }
}