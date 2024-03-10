package com.sisco.typicode.data.source.remote

import com.sisco.typicode.data.source.remote.response.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = 10
    ): Response<List<PhotosResponse>>
}