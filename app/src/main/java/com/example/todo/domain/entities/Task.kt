package com.example.todo.domain.entities

import org.joda.time.LocalDate

data class Task(
    val key: TaskKey,
    val data: TaskData
) {
    fun copyData(
        name: String = data.name,
        date: LocalDate = data.date,
        isDone: Boolean = data.isDone
    ): Task {
        return copy(key = key, data = TaskData(name, date, isDone))
    }
}

data class TaskData(
    val name: String,
    val date: LocalDate,
    val isDone: Boolean
) {
    companion object {
        val EMPTY = TaskData("", LocalDate(), isDone = false)
    }
}

