package com.example.todo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todo.ui.AddTaskFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),AddTaskFragment.OnFragmentInteractionListener {

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

    @SuppressLint("RestrictedApi")
    private fun addTaskFragment() {
        add_new_task.visibility = View.GONE
        val instance = AddTaskFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, instance, AddTaskFragment.CLASS_SIMPLE_NAME)
            .addToBackStack(AddTaskFragment.CLASS_SIMPLE_NAME)
            .commit()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        title = CLASS_SIMPLE_NAME
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count==1){
            setToolbar()
            supportFragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }
    }
    companion object{
        const val CLASS_SIMPLE_NAME = "TODO TASK"
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        add_new_task.visibility = View.VISIBLE

    }
}
