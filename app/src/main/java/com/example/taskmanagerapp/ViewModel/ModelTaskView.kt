package com.example.taskmanagerapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.repository.TaskRepo
import com.example.taskmanagerapp.utils.Resource

class ModelTaskView(application: Application) :AndroidViewModel(application){
    private val taskrepo = TaskRepo(application)

    fun insertTask(task: Task): MutableLiveData<Resource<Long>> {
        return taskrepo.insertTask(task)
    }
}