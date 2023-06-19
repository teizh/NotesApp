package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.TaskItemBinding
import com.example.notesapp.model.Task

class TaskAdapter(
    var taskList: List<Task>,
    var onLongClick: (Task) -> Unit,
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
        holder.onBind(task)

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Task) {
            binding.tvTitle.text = task.title
            binding.checkbox.isChecked = task.isDone

            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckChanged(adapterPosition, isChecked)
            }

            itemView.setOnClickListener {
                onLongClick(task)
                false
            }
        }

        private fun onTaskCheckChanged(position: Int, isChecked: Boolean) {
            val task = taskList[position]
            task.toggleDone()
            notifyItemChanged(position, isChecked)
        }
    }
}