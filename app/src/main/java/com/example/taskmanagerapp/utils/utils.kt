package com.example.taskmanagerapp.utils

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}
fun Context.longToastShow(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
fun Dialog.setupDialog(layourResId:Int) {
    setContentView(layourResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT

        )
    setCancelable(false)
}
fun clearTaskForm(editText: EditText, textTextInputLayout: TextInputLayout){
    editText.text = null
    textTextInputLayout.error = null
}
