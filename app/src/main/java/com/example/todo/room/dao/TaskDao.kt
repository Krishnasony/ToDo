package com.example.todo.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.todo.room.entity.TodoTask

@Dao
interface TaskDao {
  @Insert
  fun addTask(vararg task: TodoTask)
}