package com.example.taskmanagerapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.taskmanagerapp.databinding.ActivityMainBinding
import com.example.taskmanagerapp.utils.setupDialog
import com.google.android.material.textfield.TextInputLayout

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
        Dialog(this,R.style.DialogCustom).apply {
            setupDialog(R.layout.loading_screen)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cancelBtn = addTaskDialog.findViewById<Button>(R.id.cancelbtn)
        val updateCancelBtn = updateTaskDialog.findViewById<Button>(R.id.updatecancelbtn)

        cancelBtn?.setOnClickListener { addTaskDialog.dismiss() }
        updateCancelBtn?.setOnClickListener{ updateTaskDialog.dismiss() }


        binding.addtaskbtn.setOnClickListener{
            addTaskDialog.show()
        }
        //edit task save
        val savetaskBtn = addTaskDialog.findViewById<Button>(R.id.savebtn)
        savetaskBtn.setOnClickListener{
            val title = addTaskDialog.findViewById<TextInputLayout>(R.id.Topic)
            val content = addTaskDialog.findViewById<TextInputLayout>(R.id.content)

            if(title != null || content != null){
                Toast.makeText(this,"Fill all the fileds",Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this,"Task added successfully",Toast.LENGTH_SHORT).show()
                loadingTask.show()
            }
        }
        //update task save
        val updatetaskBtn = updateTaskDialog.findViewById<Button>(R.id.updatebtn)
        updatetaskBtn.setOnClickListener{
            val title = addTaskDialog.findViewById<TextInputLayout>(R.id.UpdateTopic)
            val content = addTaskDialog.findViewById<TextInputLayout>(R.id.Updatecontent)

            if(title != null || content != null){
                Toast.makeText(this,"Task updated successfully",Toast.LENGTH_SHORT).show()
                loadingTask.show()

            }
            else{
                Toast.makeText(this,"Fill all the fileds",Toast.LENGTH_SHORT).show()
            }
        }

    }
}