package com.example.todo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.domain.TaskRepository
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskKey
import com.example.todo.ui.AppDispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(
    private val repository: TaskRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        observeTaskFlow()
    }

    fun changeDone(key: TaskKey) {
        GlobalScope.launch(dispatchers.disk) {
            repository.checkTask(key)
        }
    }

    private fun observeTaskFlow() {
        GlobalScope.launch(dispatchers.disk) {
            repository.tasksFlow()
                .collect { list ->
                    withContext(dispatchers.main) {
                        _tasks.value = list
                    }
                }
        }
    }

}