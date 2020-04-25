package com.example.todo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.domain.TaskRepository
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        observeTaskFlow()
    }

    fun changeDone(key: TaskKey) {
        GlobalScope.launch {
            repository.checkTask(key)
        }
    }

    private fun observeTaskFlow() {
        GlobalScope.launch(Dispatchers.IO) {
            repository.tasksFlow()
                .collect { list ->
                    withContext(Dispatchers.Main) {
                        _tasks.value = list
                    }
                }
        }
    }

}