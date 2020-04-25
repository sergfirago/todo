package com.example.todo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun tasks(): Flow<List<TaskSM>>

    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<TaskSM>

    @Query("SELECT * FROM task WHERE id=:id")
    suspend fun getTask(id: Long): TaskSM


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskSM: TaskSM): Long
}