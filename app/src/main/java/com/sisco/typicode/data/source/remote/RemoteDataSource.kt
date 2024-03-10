package com.sisco.typicode.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sisco.typicode.domain.model.Photo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val remoteService: RemoteService) {

    fun getPhotos(): Pager<Int, Photo> =
        Pager(PagingConfig(pageSize = 1)) {
            PhotosPagingSource(remoteService)
        }
}