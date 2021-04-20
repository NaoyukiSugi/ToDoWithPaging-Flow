package com.richarddewan.todowithpaging_flow.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.richarddewan.todowithpaging_flow.data.paging.PagingTask
import com.richarddewan.todowithpaging_flow.data.paging.TaskPagingSource
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskPagingSource: TaskPagingSource
) : TaskRepository {
    override fun getTaskList(): Flow<PagingData<PagingTask.Task>> {
        return Pager(
            config = PagingConfig(),
            pagingSourceFactory = { taskPagingSource }
        ).flow
    }

    private fun pagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 10,
            prefetchDistance = 3,
            initialLoadSize = 30,
            maxSize = 16,
            enablePlaceholders = false
        )
    }
}
