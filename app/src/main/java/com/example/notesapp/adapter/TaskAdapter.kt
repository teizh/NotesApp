package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.TaskItemBinding
import com.example.notesapp.model.Task

class TaskAdapter(
    var taskList: List<Task>,
    private var onLongClick: (Task) -> Unit,
    private val onTaskCheckChanged: (position: Int, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.onBind(task, onTaskCheckChanged)

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

/*    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(taskList: List<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }*/

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Task, onTaskCheckChanged: (position: Int, isChecked: Boolean) -> Unit) {
            binding.tvTitle.text = task.title
            binding.checkbox.isChecked = task.isDone

            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckChanged(adapterPosition, isChecked)
            }

            itemView.setOnClickListener {
                onLongClick(task)

            }
        }

  /*      private fun onTaskCheckChanged(position: Int, isChecked: Boolean) {
            val task = taskList[position]
            task.toggleDone()
            notifyItemChanged(position, isChecked)
        }
        *//*         fun removeTask(position: Int) {
                    val tasks = taskList.toMutableList()
                    tasks.removeAt(position)
                    notifyDataSetChanged()
                }*/
    }
}