package dev.shtanko.topgithub.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import dev.shtanko.common.android.processing.image.ImageLoader
import dev.shtanko.common.android.processing.image.TransformType
import dev.shtanko.domain.entity.User
import dev.shtanko.topgithub.R

interface OnItemUserClickListener {
    fun onUserItemClick(username: String)
}

internal class MainViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
    private val userImageView: AppCompatImageView = rowView.findViewById(R.id.userImageView)
    private val userNameTextView: AppCompatTextView = rowView.findViewById(R.id.userNameTextView)
    private val avatarProgressBar: ProgressBar = rowView.findViewById(R.id.avatarProgressBar)

    internal fun bindTo(item: User, imageLoader: ImageLoader) {
        val url = item.avatarUrl
        val login = item.login

        userNameTextView.text = login

        imageLoader.load(
            source = url ?: "",
            transform = TransformType.CIRCLE_CROP,
            target = userImageView,
            placeholder = R.drawable.ic_image_placeholder,
            error = R.drawable.ic_broken_image_black,
            onLoadSucceed = {
                avatarProgressBar.visibility = View.GONE
            },
            onLoadFailed = {
                avatarProgressBar.visibility = View.GONE
            }
        )
    }
}

internal class MainAdapter(
    private val listener: OnItemUserClickListener,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<MainViewHolder>() {

    private val items: MutableList<User> = ArrayList()

    fun getLastUserId(): Int {
        return if (items.isEmpty()) 0 else items.last().id
    }

    fun addItems(items: List<User>) {
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
        holder.bindTo(item, imageLoader)
        holder.itemView.setOnClickListener {
            val login = item.login
            listener.onUserItemClick(login ?: "")
        }
    }
}