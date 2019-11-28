package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.ui.AddTaskFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        init()
    }

    private fun init(){
        add_new_task.setOnClickListener {
            addTaskFragment()
        }
    }

    private fun addTaskFragment() {
        val instance = AddTaskFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, instance, AddTaskFragment.CLASS_SIMPLE_NAME)
            .addToBackStack(AddTaskFragment.CLASS_SIMPLE_NAME)
            .commit()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = CLASS_SIMPLE_NAME
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
    companion object{
        const val CLASS_SIMPLE_NAME = "TODO TASK"
    }
}
