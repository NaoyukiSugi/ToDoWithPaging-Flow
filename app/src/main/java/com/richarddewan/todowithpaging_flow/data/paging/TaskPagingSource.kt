package com.richarddewan.todowithpaging_flow.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.richarddewan.todowithpaging_flow.data.remote.network.NetworkService
import com.richarddewan.todowithpaging_flow.data.remote.response.TaskResponse
import java.lang.Exception

class TaskPagingSource(
    private val networkService: NetworkService
) : PagingSource<Int, PagingTask.Task>(),
    ResponseMapper<TaskResponse, PagingTask> {

    override fun getRefreshKey(state: PagingState<Int, PagingTask.Task>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingTask.Task> {
        val currentPage = params.key ?: 1

        return try {
            networkService.getTaskList(currentPage).run {
                val data = mapFromResponse(this)
                LoadResult.Page(
                    data = data.tasks,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage == data.lastPage) null else currentPage + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun mapFromResponse(response: TaskResponse): PagingTask {
        return with(response) {
            PagingTask(
                total = lastPage,
                page = currentPage,
                tasks = task.map {
                    PagingTask.Task(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        body = it.body,
                        note = it.note,
                        status = it.status,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
            )
        }
    }
}
