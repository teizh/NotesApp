package com.example.notesapp.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TaskViewModel : ViewModel() {
    private val taskList = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = taskList

    fun addTask(task: String) {
        val currentList = tasks.value?.toMutableList() ?: mutableListOf()
        currentList.add(Task(task))
        taskList.value = currentList
    }

    fun toggleTask(task: Int) {
        val currentList = tasks.value?.toMutableList()
        currentList?.get(task)?.isDone = true
        taskList.value = currentList
    }

    fun deleteTask(task: Int) {
        val tasks = tasks.value?.toMutableList()
        tasks?.removeAt(task)
        taskList.value = tasks
    }
}
