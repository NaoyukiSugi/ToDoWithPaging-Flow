package com.richarddewan.todowithpaging_flow.data.paging

interface ResponseMapper<Response, Model> {
    fun mapFromResponse(response: Response): Model
}
