package com.example.taskmanagerapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagerapp.Database.TaskDB
import com.example.taskmanagerapp.models.Task
import com.example.taskmanagerapp.taskdao.TaskInterface
import com.example.taskmanagerapp.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepo(application: Application) {
    private val taskDao: TaskInterface = TaskDB.getInstance(application).taskDao()

    fun showTaskList() = flow {
        emit(Resource.Loading())
        try {
            taskDao.showTaskList().collect { tasks ->
                emit(Resource.Success(tasks))
            }
        } catch (e: Exception) {
            emit(Resource.Error<List<Task>>(e.message ?: "An error occurred"))
        }
    }

    fun insertTask(task: Task): LiveData<Resource<Long>> {
        val result = MutableLiveData<Resource<Long>>()
        result.postValue(Resource.Loading())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val taskId = taskDao.insertTask(task)
                result.postValue(Resource.Success(taskId))
            } catch (e: Exception) {
                result.postValue(Resource.Error(e.message ?: "An error occurred"))
            }
        }

        return result
    }

    fun deleteTaskById(taskid: String): LiveData<Resource<Int>> {
        val result = MutableLiveData<Resource<Int>>()
        result.postValue(Resource.Loading())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val rowsAffected = taskDao.deleteTaskUsingId(taskid)
                result.postValue(Resource.Success(rowsAffected))
            } catch (e: Exception) {
                result.postValue(Resource.Error(e.message ?: "An error occurred"))
            }
        }

        return result
    }
}