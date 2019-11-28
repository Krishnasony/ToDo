package com.example.todo

import android.app.Application
import com.example.todo.di.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin

        startKoin{
            androidLogger()
            androidContext(this@ToDoApplication)
            modules(listOf(appModule))
        }
        Stetho.initializeWithDefaults(this)

    }
}