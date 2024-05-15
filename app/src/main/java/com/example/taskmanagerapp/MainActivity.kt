package com.example.taskmanagerapp


import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagerapp.Adepter.AdepterView
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.ViewModel.ModelTaskView
import com.example.taskmanagerapp.databinding.ActivityMainBinding
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.utils.Status
import com.example.taskmanagerapp.utils.clearTaskForm
import com.example.taskmanagerapp.utils.longToastShow
import com.example.taskmanagerapp.utils.setupDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val binding :ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val addTaskDialog :Dialog by lazy {
        Dialog(this,R.style.DialogCustom).apply {
            setupDialog(R.layout.activity_new_task)
        }
    }
    private val updateTaskDialog :Dialog by lazy {
        Dialog(this,R.style.DialogCustom).apply {
            setupDialog(R.layout.activity_update_task)
        }
    }
    private val  loadingTask:Dialog by lazy {
        Dialog(this, R.style.DialogCustom).apply {
            setupDialog(R.layout.loading_screen)
        }
    }

    private val viewTaskModel: ModelTaskView by lazy {
        ViewModelProvider(this).get(ModelTaskView::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cancelBtn = addTaskDialog.findViewById<Button>(R.id.cancelbtn)
        cancelBtn?.setOnClickListener { addTaskDialog.dismiss() }

        binding.addtaskbtn.setOnClickListener {
            addTaskDialog.show()
        }

        // Edit task save
        val savetaskBtn = addTaskDialog.findViewById<Button>(R.id.savebtn)
        savetaskBtn.setOnClickListener {
            val newTitle = addTaskDialog.findViewById<TextInputEditText>(R.id.Topic)
            val newContent = addTaskDialog.findViewById<TextInputEditText>(R.id.content)

            if (newTitle != null && newContent != null) {
                val addnewTask = Task(
                    UUID.randomUUID().toString(),
                    newTitle.text.toString().trim(),
                    newContent.text.toString().trim(),
                    Date()
                )
                viewTaskModel.insertTask(addnewTask).observe(this) {
                    when (it.status) {
                        Status.LOADING -> {
                            loadingTask.show()
                        }
                        Status.ERROR -> {
                            loadingTask.dismiss()
                            it.message?.let { it1 -> longToastShow(it1) }
                        }
                        Status.SUCCESS -> {
                            loadingTask.dismiss()
                            if (it.data?.toInt() != -1) {
                                longToastShow("Task added successfully")
                                addTaskDialog.dismiss()
                            }
                        }
                    }
                }
            }
        }

        // Update task save
        val updateCancelBtn = updateTaskDialog.findViewById<Button>(R.id.updatecancelbtn)
        updateCancelBtn?.setOnClickListener { updateTaskDialog.dismiss() }

        val updatetaskBtn = updateTaskDialog.findViewById<Button>(R.id.updatebtn)
        updatetaskBtn.setOnClickListener {
            val updatetitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.UpdateTopic)
            val updateContent = updateTaskDialog.findViewById<TextInputEditText>(R.id.Updatecontent)

            if (updatetitle != null && updateContent != null) {
                val updateTask = Task(
                    "", // You need to provide the task ID here
                    updatetitle.text.toString().trim(),
                    updateContent.text.toString().trim(),
                    Date()
                )
                viewTaskModel.updateTask(updateTask).observe(this) {
                    when (it.status) {
                        Status.LOADING -> {
                            loadingTask.show()
                        }
                        Status.ERROR -> {
                            loadingTask.dismiss()
                            it.message?.let { it1 -> longToastShow(it1) }
                        }
                        Status.SUCCESS -> {
                            loadingTask.dismiss()
                            if (it.data != -1) {
                                longToastShow("Task updated successfully")
                                updateTaskDialog.dismiss()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerViewAdapter: AdepterView by lazy {
            AdepterView { type, position, task ->
                if (type == "delete") {
                    viewTaskModel.deleteTaskById(task.id).observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                loadingTask.show()
                            }
                            Status.ERROR -> {
                                loadingTask.dismiss()
                                it.message?.let { it1 -> longToastShow(it1) }
                            }
                            Status.SUCCESS -> {
                                loadingTask.dismiss()
                                if (it.data != -1) {
                                    longToastShow("Task deleted successfully")
                                    addTaskDialog.dismiss()
                                }
                            }
                        }
                    }
                } else if (type == "update") {
                    // Show the update dialog and set task details
                    updateTaskDialog.show()
                    val updatetitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.UpdateTopic)
                    val updateContent = updateTaskDialog.findViewById<TextInputEditText>(R.id.Updatecontent)
                    updatetitle.setText(task.topic)
                    updateContent.setText(task.content)
                }
            }
        }

        binding.taskRecyler.adapter = recyclerViewAdapter
        getAlltasks(recyclerViewAdapter)
    }

    private fun getAlltasks(recyclerViewAdepter:AdepterView) {
        loadingTask.show()
        CoroutineScope(Dispatchers.Main).launch {
            viewTaskModel.showTaskList().collect {
                when (it.status) {
                    Status.LOADING -> {
                        loadingTask.show()
                    }
                    Status.ERROR -> {
                        loadingTask.dismiss()
                        it.message?.let { message -> longToastShow(message) }
                    }
                    Status.SUCCESS -> {
                        it.data?.let { taskList ->
                            loadingTask.dismiss()
                            recyclerViewAdepter.addAllTasks(taskList)
                        }
                    }
                }
            }
        }
    }
}
