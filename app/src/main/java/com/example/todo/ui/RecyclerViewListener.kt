package com.example.todo.ui

import com.example.todo.room.entity.TodoTask

interface RecyclerViewListener {
    fun onClickEdit(task: TodoTask)
    fun onClickDelete(task: TodoTask)
}
