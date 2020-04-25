package com.example.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskSM::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}