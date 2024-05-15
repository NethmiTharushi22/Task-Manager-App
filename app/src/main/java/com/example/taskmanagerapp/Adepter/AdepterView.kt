package com.example.taskmanagerapp.Adepter

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class AdepterView(
    private val deleteUpdateCallback: (type:String,position:Int,task:Task)->Unit
):
RecyclerView.Adapter<AdepterView.ViewHolder>(){

    private val taskList: ArrayList<Task> = arrayListOf<Task>()
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val topic:TextView = itemView.findViewById(R.id.tasktitle)
        val content:TextView = itemView.findViewById(R.id.taskcontent)
        val date:TextView = itemView.findViewById(R.id.datecontent)

        val deleteImg:ImageView = itemView.findViewById(R.id.deletein)
        val updateImg :ImageView =itemView.findViewById(R.id.updateIn)
    }
    fun addAllTasks(newTaskList: List<Task>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(parent.context)
               .inflate(R.layout.task_card,parent ,false)
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val readtask = taskList[position]

        holder.topic.text =readtask.topic
        holder.content.text =readtask.content
        val formatDate = SimpleDateFormat("dd-mmm-yyyy HH:mm:ss a", Locale.getDefault())

        holder.date.text =formatDate.format(readtask.date)

        holder.deleteImg.setOnClickListener{
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition,readtask)
            }
        }
        holder.updateImg.setOnClickListener{
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("update",holder.adapterPosition,readtask)
            }
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}