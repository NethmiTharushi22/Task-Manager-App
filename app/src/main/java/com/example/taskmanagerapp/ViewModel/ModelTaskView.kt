package com.example.taskmanagerapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.taskmanagerapp.repository.TaskRepo
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.utils.Resource

class ModelTaskView(application: Application) : AndroidViewModel(application) {
    private val taskRepo: TaskRepo = TaskRepo(application)

    fun showTaskList() = taskRepo.showTaskList()
    fun insertTask(task: Task) = taskRepo.insertTask(task)
}