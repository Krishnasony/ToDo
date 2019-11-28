package com.example.todo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import com.example.todo.R

class  EditTextWithClearButton : androidx.appcompat.widget.AppCompatEditText {

    private val onTouchListener = OnTouchListener { _, event ->
        if (event?.action == MotionEvent.ACTION_UP) {
            if (this@EditTextWithClearButton.width - event.x < getPx(40)) {
                this.text = null
                if (onClearButtonClickListener != null) {
                    onClearButtonClickListener!!.onClick()
                }
            }
        }
        false
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context) : super(context) {
        val compoundDrawables = this.compoundDrawables
        if (compoundDrawables[2] == null) {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_black_24dp, 0)
        }
        this.setOnTouchListener(onTouchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val compoundDrawables = this.compoundDrawables
        if (compoundDrawables[2] == null) {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_black_24dp, 0)
        }
        this.setOnTouchListener(onTouchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val compoundDrawables = this.compoundDrawables
        if (compoundDrawables[2] == null) {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_black_24dp, 0)
        }
        this.setOnTouchListener(onTouchListener)
    }

    private fun getPx(dp: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()

    private var onClearButtonClickListener: OnClearButtonClickListener? = null

    fun setOnClearButtonClickListener(onClearButtonClickListener: OnClearButtonClickListener) {
        this.onClearButtonClickListener = onClearButtonClickListener
    }

    interface OnClearButtonClickListener {
        fun onClick()
    }
}
