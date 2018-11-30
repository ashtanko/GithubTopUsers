package me.shtanko.topgithub.ui.main

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.shtanko.topgithub.R
import me.shtanko.topgithub.image.GlideApp

interface OnItemUserClickListener {
    fun onUserItemClick(userId: Int)
}

internal class MainViewHolder(rowView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(rowView) {
    private val userImageView: AppCompatImageView = rowView.findViewById(R.id.userImageView)
    private val userNameTextView: AppCompatTextView = rowView.findViewById(R.id.userNameTextView)
    private val avatarProgressBar: ProgressBar = rowView.findViewById(R.id.avatarProgressBar)

    internal fun bindTo(item: Triple<String, String, Int>) {
        val url = item.first
        val login = item.second

        userNameTextView.text = login

        GlideApp.with(userImageView.context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    avatarProgressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    avatarProgressBar.visibility = View.GONE
                    return false
                }

            })
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(userImageView)

    }
}

internal class MainAdapter(
    private val listener: OnItemUserClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<MainViewHolder>() {

    private val items: MutableList<Triple<String, String, Int>> = ArrayList()

    fun getLastUserId(): Int {
        return if (items.isEmpty()) 0 else items.last().third
    }

    fun addItems(items: List<Triple<String, String, Int>>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        holder.bindTo(item)
        holder.itemView.setOnClickListener {
            val userId = item.third
            listener.onUserItemClick(userId)
        }
    }
}