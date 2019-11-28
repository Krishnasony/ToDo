package com.example.todo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todo.repo.TaskRepo
import com.example.todo.room.database.AppDataBase
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask

class ToDoTaskViewModel(application: Application):AndroidViewModel(application) {
    private val dao = AppDataBase.getDatabase(application).taskDao
    private val repo = TaskRepo(dao)
    lateinit var categoryListLiveData:LiveData<List<Category>>

    suspend fun addCategory(category: Category){
        repo.addCategory(category)
    }
    suspend fun getAllCategory(){
        categoryListLiveData = repo.getAllCategory()
    }


    suspend fun addTodoTask(task: TodoTask){
        repo.addTask(task)
    }
}