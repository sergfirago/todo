package com.example.todo.di

import androidx.room.Room
import com.example.todo.data.TaskRepositoryImpl
import com.example.todo.data.db.TaskDao
import com.example.todo.data.db.TaskDataBase
import com.example.todo.domain.TaskRepository
import com.example.todo.ui.details.DetailViewModel
import com.example.todo.ui.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single<TaskDataBase> {
        Room.databaseBuilder(
            get(),
            TaskDataBase::class.java, "database-name"
        ).build()
    }
    single<TaskDao> { get<TaskDataBase>().taskDao() }
    viewModel { ListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}