package com.example.todo.data.db

import com.example.todo.data.TaskKeyImpl
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskData

internal fun TaskSM.toDomain(): Task {
    return Task(
        TaskKeyImpl.create(id),
        TaskData(name, date, done)
    )
}
private const val AUTO_GENERATE_ID: Long = 0

internal fun TaskData.toNewTaskSM() = TaskSM(
    AUTO_GENERATE_ID, name, date, isDone
)

internal fun Task.toTaskSM() = TaskSM(
    (key as TaskKeyImpl).id, data.name, data.date, data.isDone
)