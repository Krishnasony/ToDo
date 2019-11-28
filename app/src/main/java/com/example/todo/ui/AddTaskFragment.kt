package com.example.todo.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.room.database.AppDataBase
import com.example.todo.room.entity.Category
import com.example.todo.room.entity.TodoTask
import com.example.todo.utils.*
import com.example.todo.viewModel.ToDoTaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.layout_add_category_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private var category = Category()
    private var task = TodoTask()
    private var taskDate = currentDate
    private val mViewModel:ToDoTaskViewModel by viewModel()
    private val database:AppDataBase by inject()
    private  var categoryEdit : Array<String>? = null
    private  var categoryName : String? = null
    private var taskCategoryList : ArrayList<String> = arrayListOf()
    private var listener: OnFragmentInteractionListener? = null
    private var mode = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getInt(MODE)!=0){
                mode = it.getInt(MODE)
                task = it.getParcelable(DATA)!!
            }
        }
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
        setToolbar()
        setEditData()
        init()
        getCategoryListData()
    }

    private fun setEditData() {
        sp_category_name.prompt = task.categoryName
        ed_title.setText(task.title)
        ed_task_note.setText(task.task)
        tv_task_date.text = Utils.format(task.createDate,Utils.DATE_FORMAT_dd_MMM_yyyy)
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
        tv_add_category.setOnClickListener {
                showCustomDialog()
        }
        tv_task_date.text = Utils.format(taskDate, Utils.DATE_FORMAT_dd_MMM_yyyy)
        tv_task_date.setOnClickListener {
            Utils.datePicker(
                context = context!!,
                listener = object : OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, timeInMillis: Long) {
                        taskDate = timeInMillis
                        tv_task_date.text = Utils.format(timeInMillis, Utils.DATE_FORMAT_dd_MMM_yyyy)
                    }
                }).show()
        }

        bt_new_task_done.setOnClickListener{
            addTaskToDataBase()
        }
        bt_new_task_cancel.setOnClickListener{
            listener?.onBackPressed()
        }
    }

    private fun addTaskToDataBase() {
        GlobalScope.launch(Dispatchers.IO){
            getEntries()
            if (mode==1){
                mViewModel.updateTodoTask(task)
            }else{
                mViewModel.addTodoTask(task)
            }
        }
        listener?.onBackPressed()
    }

    private fun getEntries() {
        with(task){
            this.title = this@AddTaskFragment.ed_title.text.toString()
            this.categoryName = this@AddTaskFragment.categoryName!!
            this.createDate = this@AddTaskFragment.taskDate
            this.task = this@AddTaskFragment.ed_task_note.text.toString()

        }
    }

    private fun setToolbar(){
        val toolbar = (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = CLASS_SIMPLE_NAME
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            listener?.onBackPressed()
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.selectedItemPosition
        categoryName  = taskCategoryList[p2]
    }

    private fun showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = (view!!.parent as ViewGroup)
        val dialogView = LayoutInflater.from(context!!).inflate(R.layout.layout_add_category_item, viewGroup, false)
        val builder = AlertDialog.Builder(context!!)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        dialogView.create_subcategory_cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.create_subcategory_done.setOnClickListener {
            val itemName:String? = dialogView.category_item_name.editableText.toString().trim()
            if (itemName!=null && itemName.isNotEmpty()) {
                context!!.showToast("$itemName")
                alertDialog.dismiss()
                GlobalScope.launch(Dispatchers.IO) {
                    category.id =0
                    category.category_name = itemName
                    categoryName = itemName
                    mViewModel.addCategory(Category(category_name = itemName))

                }
                getCategoryListData()
                alertDialog.dismiss()
            } else {
                context!!.showToast("Please Enter Item Name")
            }
        }
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }
    interface OnFragmentInteractionListener {

        fun onBackPressed()
    }

    companion object{
        const val CLASS_SIMPLE_NAME = "Add Task"
        private const val MODE ="mode"
        private const val DATA = "Data"
        const val EDIT:Int = 1

        @JvmStatic
        fun newInstance() = AddTaskFragment()

        @JvmStatic
        fun newInstance(mMode:Int,task: TodoTask) = AddTaskFragment().apply {
                arguments = Bundle().apply {
                    this.putInt(MODE,mMode)
                    this.putParcelable(DATA,task)
                }
        }
    }

}
