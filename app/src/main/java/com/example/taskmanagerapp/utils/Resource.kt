package com.example.taskmanagerapp.utils

 sealed class Resource<T> (val status: Status, data:T? = null, val message :String? =null){
    class Success<T>(data: T?):Resource<T>(Status.SUCCESS,data)
     class Error<T>(message: String,data: T? = null):Resource<T>(Status.ERROR,data)
     class Loading<T>:Resource<T>(Status.LOADING)

 }