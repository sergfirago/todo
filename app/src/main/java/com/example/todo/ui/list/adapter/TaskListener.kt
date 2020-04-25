package com.example.todo.ui.list.adapter

import com.example.todo.domain.entities.TaskKey

interface TaskListener{
    fun onClick(key: TaskKey)
    fun onTaskDoneClick(key: TaskKey)
}