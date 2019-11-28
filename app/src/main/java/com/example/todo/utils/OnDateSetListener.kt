package com.example.todo.utils

import android.widget.DatePicker

interface OnDateSetListener {
    fun onDateSet(view: DatePicker?, timeInMillis:Long)
}