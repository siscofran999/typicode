package com.sisco.typicode.data.source.remote

import com.sisco.typicode.domain.model.Photo
import com.sisco.typicode.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val remoteService: RemoteService) {
    fun getPhotos(): Flow<DataState<List<Photo>>> = flow {
        emit(DataState.Loading)
        val response = remoteService.getPhotos()
        if (response.isSuccessful) {
            response.body()?.let { photosResponses ->
                emit(DataState.Success(photosResponses.map {
                    it.toDomain()
                }))
            }
        }else {
            emit(DataState.Error(response.message()))
        }
    }.catch { t ->
        emit(DataState.Error(t.localizedMessage))
    }.flowOn(Dispatchers.IO)
}