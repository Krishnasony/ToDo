package com.example.todo.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.room.database.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            AppDataBase.DATABASE_NAME
        ).allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
}