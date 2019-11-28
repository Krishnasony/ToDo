package com.example.todo.repo

import com.example.todo.room.dao.TaskDao
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepo(private val taskDao: TaskDao) {

    suspend fun addCategory(category: Category) = withContext(Dispatchers.IO){
        taskDao.addCategory(category)
    }

    suspend fun getAllCategory() = withContext(Dispatchers.IO){
        taskDao.getAllCategoryList()
    }

    suspend fun addTask(task: TodoTask) = withContext(Dispatchers.IO){
        taskDao.addTask(task)
    }

    suspend fun getAllTask() = withContext(Dispatchers.IO){
        taskDao.getAllTaskList()
    }

    suspend fun updateTask(task: TodoTask) = withContext(Dispatchers.IO){
        taskDao.updateTask(task)
    }
    suspend fun delete(task: TodoTask) = withContext(Dispatchers.IO){
        taskDao.deleteTask(task)
    }
}