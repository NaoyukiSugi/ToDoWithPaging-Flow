package com.richarddewan.todowithpaging_flow.data.remote.network

import com.richarddewan.todowithpaging_flow.data.remote.response.TaskResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers(Network.HEADER_ACCEPT)
    @GET(Endpoint.GET_TASK_LIST)
    suspend fun getTaskList(@Query("page") pageNumber: Int): TaskResponse
}
