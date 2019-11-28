package com.example.todo.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.room.dao.TaskDao
import com.example.todo.room.database.AppDataBase.Companion.VERSION
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask

@Database(
    entities = [
        TodoTask::class,
        Category::class
    ],
    version = VERSION,
    exportSchema = false

)
abstract class AppDataBase:RoomDatabase() {
    abstract val taskDao:TaskDao

    companion object{
        const val DATABASE_NAME="todo_task.db"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(
            context: Context
        ): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATABASE_NAME
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .setJournalMode(JournalMode.TRUNCATE)

                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}