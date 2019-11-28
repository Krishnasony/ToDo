package com.example.todo.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask

@Dao
interface TaskDao {
  @Insert
  fun addTask(vararg task: TodoTask)

  @Update
  fun updateTask(vararg task: TodoTask)

  @Insert
  fun addCategory(vararg category: Category)

  @Query("SELECT COUNT(*) FROM category")
  fun countAllCategories(): Int

  @Query("SELECT * FROM category")
  fun getAllCategoryList():LiveData<List<Category>>

  @Query("SELECT * FROM todo_task")
  fun getAllTaskList():LiveData<List<TodoTask>>

  @Delete
  fun deleteTask(task: TodoTask)
}