package com.richarddewan.todowithpaging_flow.data.repository

import androidx.paging.PagingData
import com.richarddewan.todowithpaging_flow.data.paging.PagingTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTaskList(): Flow<PagingData<PagingTask.Task>>
}
