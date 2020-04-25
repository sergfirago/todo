package com.example.todo.di

import com.example.todo.ui.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext

private const val DISK_CONTEXT_NAME = "disk io context"

internal class AppDispatchersImpl : AppDispatchers {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val disk: CoroutineDispatcher
        get() = newSingleThreadContext(DISK_CONTEXT_NAME)
}
