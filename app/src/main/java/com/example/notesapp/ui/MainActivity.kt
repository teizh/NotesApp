package com.example.notesapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.adapter.TaskAdapter
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.model.Task
import com.example.notesapp.model.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskAdapter = TaskAdapter(emptyList(), this::onLongClick) { task, _ ->
            viewModel.toggleTask(task)
        }

        binding.taskList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        binding.addButton.setOnClickListener {
            val title = binding.taskInput.text.toString().trim()
            if (title.isNotEmpty()) {
                viewModel.addTask(title)
            }
        }

        viewModel.tasks.observe(this) { taskList ->
            if (taskList != null) {
                taskAdapter.taskList = taskList
            }
            binding.taskList.post {
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onLongClick(task: Task) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete Task?")
        alertDialog.setNegativeButton("No"
        ) { dialog, _ -> dialog?.cancel() }

        alertDialog.setPositiveButton("Yes"
        ) { _, _ -> viewModel.deleteTask(0) }
        alertDialog.create().show()
    }
}
