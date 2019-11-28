package com.example.todo.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask

@Dao
interface TaskDao {
  @Insert
  fun addTask(vararg task: TodoTask)

  @Insert
  fun addCategory(vararg category: Category)

  @Query("SELECT COUNT(*) FROM category")
  fun countAllCategories(): Int

  @Query("SELECT * FROM category")
  fun getAllCategoryList():LiveData<List<Category>>
}