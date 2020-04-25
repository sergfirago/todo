package com.example.todo.data

import com.example.todo.data.db.TaskDao
import com.example.todo.data.db.toDomain
import com.example.todo.data.db.toNewTaskSM
import com.example.todo.data.db.toTaskSM
import com.example.todo.domain.TaskRepository
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskData
import com.example.todo.domain.entities.TaskKey
import com.example.todo.ui.AppDispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


internal class TaskRepositoryImpl constructor(
    private val dao: TaskDao,
    dispatchers: AppDispatchers
) : TaskRepository {
    private val taskChannel = BroadcastChannel<List<Task>>(1)

    init {
        GlobalScope.launch(dispatchers.disk) {
            dao.tasks().collect { list ->
                taskChannel.send(list.map { it.toDomain() })
            }
        }
    }

    override suspend fun getTasks(): List<Task> {
        return dao.getTasks().map { it.toDomain() }
    }

    override suspend fun getTask(key: TaskKey): Task {
        key as TaskKeyImpl
        return dao.getTask(key.id).toDomain()
    }

    override suspend fun addTask(taskData: TaskData): Task {
        val key = dao.insert(taskData.toNewTaskSM())
        return getTask(TaskKeyImpl.create(key))
    }

    override suspend fun checkTask(key: TaskKey): Task {
        val task = getTask(key)
        return updateTask(task.copyData(isDone = true))
    }

    override suspend fun updateTask(task: Task): Task {
        val key = task.key as TaskKeyImpl
        dao.insert(task.toTaskSM())
        return getTask(key)
    }

    override suspend fun tasksFlow(): Flow<List<Task>> {
        return dao.tasks().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun refreshTasks() {
        taskChannel.send(getTasks())
    }

}