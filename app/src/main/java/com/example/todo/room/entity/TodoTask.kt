package com.example.todo.room.entity

import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "todo_task")
data class TodoTask(
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "task") @SerializedName("task") val task: String,
    @ColumnInfo(name = "create_date") @SerializedName("create_date") val createDate: Long
):ViewModel() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryKey")
    var primaryKey: Int = 0

    constructor():this(
        title ="",
        task = "",
        createDate = 0L
    )
}