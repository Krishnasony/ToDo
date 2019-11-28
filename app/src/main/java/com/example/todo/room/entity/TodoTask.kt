package com.example.todo.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_task")
@Parcelize
data class TodoTask(
    @ColumnInfo(name = "title") @SerializedName("title") var title: String,
    @ColumnInfo(name = "task") @SerializedName("task") var task: String,
    @ColumnInfo(name = "category_name") @SerializedName("category_name")var categoryName:String,
    @ColumnInfo(name = "create_date") @SerializedName("create_date") var createDate: Long
):Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryKey")
    var primaryKey: Int = 0

    constructor():this(
        title ="",
        task = "",
        categoryName = "",
        createDate = 0L
    )
}