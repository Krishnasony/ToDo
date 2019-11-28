package com.example.todo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import androidx.lifecycle.Observer
import com.example.todo.room.entity.TodoTask
import com.example.todo.ui.AddTaskFragment
import com.example.todo.ui.TaskListAdapter
import com.example.todo.utils.observeOnce
import com.example.todo.viewModel.ToDoTaskViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),AddTaskFragment.OnFragmentInteractionListener {

    private var adapter:TaskListAdapter ?= null
    private var taskList:ArrayList<TodoTask> = arrayListOf()
    private val mViewModel:ToDoTaskViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        init()
        getTaskDataList()
    }

    private fun init(){
        add_new_task.setOnClickListener {
            addTaskFragment()
        }
        val flexBoxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        rv_task.layoutManager = flexBoxLayoutManager
        adapter = TaskListAdapter(taskList,this)
        rv_task.adapter = adapter

    }

    private fun getTaskDataList(){
        GlobalScope.launch(Dispatchers.Main){
            mViewModel.getAllTaskData()
            mViewModel.allTaskListLiveData.observeOnce(this@MainActivity, Observer {
                list->
                list?.let{
                    adapter!!.task = list as ArrayList<TodoTask>
                    adapter!!.notifyDataSetChanged()
                }
            })

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
        toolbar.setNavigationIcon(R.drawable.ic_done)
        title = CLASS_SIMPLE_NAME
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count==1){
            setToolbar()
            add_new_task.visibility = View.VISIBLE
            supportFragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }
    }
    companion object{
        const val CLASS_SIMPLE_NAME = "TODO TASK"
    }

}
