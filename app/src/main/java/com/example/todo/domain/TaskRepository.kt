package com.example.todo.domain

import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskData
import com.example.todo.domain.entities.TaskKey
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks():List<Task>
    suspend fun getTask(key: TaskKey): Task
    suspend fun addTask(taskData: TaskData): Task
    suspend fun checkTask(key: TaskKey): Task
    suspend fun updateTask(task: Task): Task
    suspend fun tasksFlow(): Flow<List<Task>>
    suspend fun refreshTasks()
}
