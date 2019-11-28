package com.example.todo.repo

import com.example.todo.room.dao.TaskDao
import com.example.todo.room.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepo(private val taskDao: TaskDao) {

    suspend fun addCategory(category: Category) = withContext(Dispatchers.IO){
        taskDao.addCategory(category)
    }

    suspend fun getAllCategory() = withContext(Dispatchers.IO){
        taskDao.getAllCategoryList()
    }
}