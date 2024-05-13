package com.example.taskmanagerapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagerapp.Database.TaskDB
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.taskdao.TaskInterface
import com.example.taskmanagerapp.utils.Resource
import com.example.taskmanagerapp.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepo (application: Application){
    private val taskDao:TaskInterface = TaskDB.getInstance(application).taskDao

    fun insertTask(task: Task) = MutableLiveData<Resource<Long>>().apply {
        postValue(Loading())
        try {
                CoroutineScope(Dispatchers.IO).launch {
                    val result = taskDao.insertTask(task)
                    postValue(Success(result))
                }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))

        }
    }
}