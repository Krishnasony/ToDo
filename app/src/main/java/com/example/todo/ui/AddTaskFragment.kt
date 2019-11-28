package com.example.todo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.todo.R
import com.example.todo.room.database.AppDataBase
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask
import com.example.todo.utils.observeOnce
import com.example.todo.viewModel.ToDoTaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private var category = Category()
    private var task = TodoTask()
    private val mViewModel:ToDoTaskViewModel by viewModel()
    private val database:AppDataBase by inject()
    private  var categoryEdit : Array<String>? = null
    private  var categoryName : String? = null
    private var taskCategoryList : ArrayList<String> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.IO) {
            if (database.taskDao.countAllCategories() == 0) {
                categoryEdit = resources.getStringArray(R.array.taskCategory)
                for (i in categoryEdit!!.indices) {
                    category.category_name = categoryEdit!![i]
                    category.id = 0
                    mViewModel.addCategory(category)
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getCategoryListData()
    }

    private fun getCategoryListData() {
        GlobalScope.launch(Dispatchers.Main) {
            mViewModel.getAllCategory()
            mViewModel.categoryListLiveData.observeOnce(this@AddTaskFragment, Observer {
                list->
                list?.let {
                    list.forEach {
                        taskCategoryList.add(it.category_name)
                    }
                    val dataAdapter =
                        ArrayAdapter<String>(
                            context!!,
                            android.R.layout.simple_spinner_item,
                            taskCategoryList
                        )

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    sp_category_name.adapter = dataAdapter
                    sp_category_name.setSelection(taskCategoryList.indexOf(categoryName))
                }
            })
        }
    }

    private fun init(){
        sp_category_name.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }


    companion object{
        const val CLASS_SIMPLE_NAME = "Add Task"
        @JvmStatic
        fun newInstance() = AddTaskFragment()
    }

}
