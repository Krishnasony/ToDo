package com.example.todo.ui

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.room.entity.TodoTask
import com.example.todo.utils.Utils

class TaskListAdapter(var mView:RecyclerViewListener, var task: ArrayList<TodoTask>,var context: Context) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
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
        p0.bind(task[p1],context,mView)
        p0.setIsRecyclable(false)
    }

    class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind( task: TodoTask,
                  context: Context,
                  mView: RecyclerViewListener
        ) {
            val viewTitle = itemView.findViewById<TextView>(R.id.textView_title)
            val cardView = itemView.findViewById<CardView>(R.id.cardView)
            val viewNote = itemView.findViewById<TextView>(R.id.textView_task_note)
            val viewCategory = itemView.findViewById<TextView>(R.id.textView_category)
            val viewDate = itemView.findViewById<TextView>(R.id.textView_task_date)

            viewTitle.text  = Utils.TITLE.plus(task.title)
            viewCategory.text = Utils.CATEGORY.plus(task.categoryName)
            viewDate.text = Utils.DATE.plus(Utils.format(task.createDate,Utils.DATE_FORMAT_dd_MMM_yyyy))
            viewNote.text = Utils.NOTE.plus(task.task)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cardView.backgroundTintList = Utils.getMaterialColor(context)!!
            }
            itemView.setOnClickListener {
                setPopMenu(task,context,mView,it)
            }
        }

        private fun setPopMenu(
            task: TodoTask,
            context: Context,
            mView: RecyclerViewListener,
            it: View) {
            val popupMenu = PopupMenu(context,it)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    return when (p0!!.itemId) {
                        R.id.edit-> {
                            mView.onClickEdit(task)
                            return true
                        }
                        R.id.delete->{
                            mView.onClickDelete(task)
                            return true
                        }

                        else ->  true
                    }
                }

            })
            popupMenu.inflate(R.menu.menu_task_item)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception){
                Log.e(e.message, "Error showing menu icons.")
            } finally {
                popupMenu.show()
            }
        }

    }
}