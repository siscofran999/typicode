package com.sisco.typicode.domain.repository

import androidx.paging.Pager
import com.sisco.typicode.utils.DataState
import com.sisco.typicode.domain.model.Login
import com.sisco.typicode.domain.model.Photo
import com.sisco.typicode.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun insertUser(user: User)
    fun getUserFromEmail(login: Login): Flow<DataState<User>>
    fun getUserForAdmin(email: String): Flow<List<User>>
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
    fun getPhotos(): Pager<Int, Photo>
}