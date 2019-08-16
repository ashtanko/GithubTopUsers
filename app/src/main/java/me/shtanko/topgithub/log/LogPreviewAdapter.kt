package me.shtanko.topgithub.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import me.shtanko.topgithub.R

class LogPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val text: AppCompatEditText = itemView.findViewById(R.id.text)
    private var lines = listOf<String>()
    private var index = 0

    fun bind(lines: List<String>, index: Int) {
        this.lines = lines
        this.index = index

        text.setText(lines[index])
    }
}

class LogPreviewAdapter : RecyclerView.Adapter<LogPreviewViewHolder>() {

    private var lines = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogPreviewViewHolder {
        return LogPreviewViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_log_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun onBindViewHolder(holder: LogPreviewViewHolder, position: Int) {
        holder.bind(lines, position)
    }

    fun setText(@NonNull text: String) {
        lines = text.split("\n".toRegex())
        notifyDataSetChanged()
    }
}