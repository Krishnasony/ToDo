package com.example.todo.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.example.todo.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @JvmStatic
    val DATE_FORMAT_dd_MMM_yyyy = "dd MMM yyyy"

    @JvmStatic
    val TITLE = "Title : "
    @JvmStatic
    val CATEGORY = "Category : "
    @JvmStatic
    val NOTE = "NOTE : "
    @JvmStatic
    val DATE = "Task Date : "

    fun datePicker(context: Context, listener: OnDateSetListener): DatePickerDialog {

        val calendar = Calendar.getInstance()

        return DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                listener.onDateSet(view, calendar.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun format(timeInMillis: Long, format: String): String {

        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        val instance = Calendar.getInstance()
        instance.timeInMillis = timeInMillis
        return sdf.format(instance.time)
    }

    fun getMaterialColor(context: Context) : ColorStateList? {
        val colors = arrayListOf(
            R.color.md_blue_grey_400,
            R.color.md_indigo_400,
            R.color.md_deep_purple_400,
            R.color.md_blue_400,
            R.color.md_orange_400,
            R.color.md_purple_400,
            R.color.md_red_400,
            R.color.md_yellow_400
        )
        return ContextCompat.getColorStateList(context,colors[(Math.random() * 6).toInt()])
    }


}