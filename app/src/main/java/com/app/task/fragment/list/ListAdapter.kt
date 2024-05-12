package com.it22564436.task.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.it22564436.task.R
import com.it22564436.task.Task.Task

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var taskList = mutableListOf<Task>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.itemView.findViewById<TextView>(R.id.Name).text = currentItem.name
        holder.itemView.findViewById<TextView>(R.id.Description).text = currentItem.description
        holder.itemView.findViewById<TextView>(R.id.Priority).text = currentItem.priority.toString()
        holder.itemView.findViewById<TextView>(R.id.Deadline).text = currentItem.deadline.toString()

        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }

    }

    fun setData(task: List<Task>){
        this.taskList = task.toMutableList()
        notifyDataSetChanged()
    }

}
