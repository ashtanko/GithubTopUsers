package me.shtanko.topgithub.ui.main

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.shtanko.topgithub.R
import me.shtanko.topgithub.ui.main.image.GlideApp

interface OnItemUserClickListener {
    fun onUserItemClick(userId: Int)
}

internal class MainViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
    val userImageView: AppCompatImageView = rowView.findViewById(R.id.userImageView)
    val userNameTextView: AppCompatTextView = rowView.findViewById(R.id.userNameTextView)

    internal fun bindTo(item: Triple<String, String, Int>) {
        val url = item.first
        val login = item.second

        userNameTextView.text = login

        GlideApp.with(userImageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(userImageView)

    }
}

internal class MainAdapter(
    private val listener: OnItemUserClickListener
) : RecyclerView.Adapter<MainViewHolder>() {

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