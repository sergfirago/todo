package com.example.todo.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.domain.TaskRepository
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskData
import com.example.todo.domain.entities.TaskKey
import com.example.todo.ui.AppDispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val repository: TaskRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private var editMode: Boolean = false
    private var taskKey: TaskKey? = null
    private var taskData: TaskData? = null
    private val _task = MutableLiveData<TaskData>()
    private val _back = MutableLiveData<Boolean>()
    val task: LiveData<TaskData> = _task
    val back: LiveData<Boolean> = _back

    fun openEditMode(key: TaskKey) = GlobalScope.launch(dispatchers.disk) {
        editMode = true
        taskKey = key
        taskData = repository.getTask(key).data
        withContext(dispatchers.main) {
            _task.value = taskData
        }
    }

    fun openInsertMode() {
        editMode = false
        taskData = TaskData.EMPTY
    }

    fun buttonAction(name: String) = GlobalScope.launch(dispatchers.main) {
        taskData?.let { data ->
            withContext(dispatchers.disk) {
                if (editMode) {
                    updateName(data, name)
                } else {
                    insertNewData(data, name)
                }
            }
        }
        _back.value = true
    }

    private suspend fun insertNewData(
        data: TaskData,
        name: String
    ) = repository.addTask(data.copy(name = name))

    private suspend fun updateName(
        data: TaskData,
        name: String
    ) {
        taskKey?.let {
            repository.updateTask(
                Task(key = it, data = data.copy(name = name))
            )
        }
    }
}
