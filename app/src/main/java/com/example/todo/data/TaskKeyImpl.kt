package com.example.todo.data

import com.example.todo.domain.entities.TaskKey
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class TaskKeyImpl(
    val id: Long
) : TaskKey {
    companion object {
        fun create(id: Long) = TaskKeyImpl(id)
    }
}