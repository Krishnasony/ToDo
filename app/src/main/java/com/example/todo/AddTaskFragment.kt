package com.example.todo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.room.entity.TodoTask

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }


    companion object{
        const val CLASS_SIMPLE_NAME = "Add Task"
        @JvmStatic
        fun newInstance() = AddTaskFragment()
    }

}
