package com.example.todo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.room.entity.TodoTask

class TaskListAdapter(var task: ArrayList<TodoTask>,var context: Context) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TaskListViewHolder {
        return TaskListViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_task_item,p0,false))
    }

    override fun getItemCount(): Int {
        return task.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: TaskListViewHolder, p1: Int) {
        p0.bind(task[p1],context)
        p0.setIsRecyclable(false)
    }

    class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind( task: TodoTask,
                  context: Context
        ) {
            val viewTitle = itemView.findViewById<TextView>(R.id.textView_title)
            viewTitle.text  = task.title
            itemView.setOnClickListener {
            }
        }

    }
}