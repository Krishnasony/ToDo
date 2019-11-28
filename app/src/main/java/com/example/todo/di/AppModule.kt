package com.example.todo.di

import com.example.todo.ToDoApplication
import com.example.todo.viewModel.ToDoTaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ToDoTaskViewModel(get()) }
    single {
        androidContext() as ToDoApplication
    }
}