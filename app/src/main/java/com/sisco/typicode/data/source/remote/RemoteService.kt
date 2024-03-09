package com.sisco.typicode.data.source.remote

import com.sisco.typicode.data.source.remote.response.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET

interface RemoteService {

    @GET("photos?_page=1&_limit=10")
    suspend fun getPhotos(): Response<List<PhotosResponse>>
}