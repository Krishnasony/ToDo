package com.example.todo.room.entity

import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")
data class Category (@ColumnInfo(name = "category_name") @SerializedName("category_name") var category_name: String
): ViewModel(){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") var id: Int = 0
    constructor() : this(
    category_name =  ""
    )

}