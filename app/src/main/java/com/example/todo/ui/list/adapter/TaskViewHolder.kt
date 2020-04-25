package com.example.todo.ui.list.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.domain.entities.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context = itemView.context

    fun bind(task: Task, listener: TaskListener) {
        itemView.name.text = task.data.name
        if (task.data.isDone) {
            itemView.done.text = context.getString(R.string.done)
            itemView.done.setTextColor(ContextCompat.getColor(context, R.color.palateTaskDone))
        } else {
            itemView.done.text = context.getString(R.string.plan)
            itemView.done.setTextColor(ContextCompat.getColor(context, R.color.palateTaskOpen))
        }
        itemView.setOnClickListener {
            listener.onClick(task.key)
        }
        itemView.done.setOnClickListener {
            listener.onTaskDoneClick(task.key)
        }
    }
}
