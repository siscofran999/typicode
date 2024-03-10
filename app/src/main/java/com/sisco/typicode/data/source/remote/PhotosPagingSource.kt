package com.sisco.typicode.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sisco.typicode.domain.model.Photo
import kotlinx.coroutines.delay

class PhotosPagingSource(
    private val remoteService: RemoteService
): PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1
            if (currentPage != 1) delay(2_000L)
            val response = remoteService.getPhotos(currentPage)
            val responseData = response.body()?.map {
                it.toDomain()
            }

            LoadResult.Page(
                data = responseData?: emptyList(),
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}