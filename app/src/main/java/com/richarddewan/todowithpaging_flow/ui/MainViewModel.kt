package com.richarddewan.todowithpaging_flow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.richarddewan.todowithpaging_flow.data.paging.PagingTask
import com.richarddewan.todowithpaging_flow.data.repository.TaskRepositoryImpl
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repositoryImpl: TaskRepositoryImpl
) : ViewModel() {
    fun getTaskList(): Flow<PagingData<PagingTask.Task>> =
        repositoryImpl.getTaskList()
            .cachedIn(viewModelScope)
}
