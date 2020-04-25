package com.example.todo.data

import com.example.todo.domain.TaskRepository
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskData
import com.example.todo.domain.entities.TaskKey
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import org.joda.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

internal class TaskRepositoryStub : TaskRepository {
    private val tasks = ConcurrentHashMap<TaskKeyImpl, Task>()
    private val refresh = BroadcastChannel<List<Task>>(1)

    init {
        val key = TaskKeyImpl.create(1)
        tasks[key] = Task(key, TaskData("one", LocalDate(), false))
    }

    override suspend fun getTasks(): List<Task> {
        return tasks.values.toList()
    }

    override suspend fun getTask(key: TaskKey): Task {
        return tasks[key]!!
    }

    override suspend fun addTask(taskData: TaskData): Task {
        val newKey = newKey()
        val newTask = Task(key = newKey, data = taskData)
        tasks[newKey] = newTask
        sentRefreshEvent()
        return newTask
    }

    override suspend fun checkTask(key: TaskKey): Task {
        key as TaskKeyImpl
        tasks[key] = tasks[key]!!.copyData(isDone = true)
        sentRefreshEvent()
        return tasks[key]!!
    }

    override suspend fun tasksFlow(): Flow<List<Task>> {
        return refresh.asFlow().onStart {
            emit(getTasks())
        }
    }

    override suspend fun updateTask(task: Task): Task {
        val key = getTaskKeyImpl(task)
        tasks[key] = task
        sentRefreshEvent()
        println("after send")
        return task
    }

    override suspend fun refreshTasks() {
        sentRefreshEvent()
    }

    private fun newKey(): TaskKeyImpl {
        val maxId = (tasks.keys.maxBy { it.id }?.id ?: 0)
        return TaskKeyImpl.create(maxId + 1)
    }

    private suspend fun sentRefreshEvent() = refresh.send(tasks.values.toList())

    private fun getTaskKeyImpl(task: Task): TaskKeyImpl {
        return task.key as TaskKeyImpl
    }
}