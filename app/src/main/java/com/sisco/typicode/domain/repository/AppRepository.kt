package com.sisco.typicode.domain.repository

import com.sisco.typicode.utils.DataState
import com.sisco.typicode.domain.model.Login
import com.sisco.typicode.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun insertUser(user: User)
    fun getUserFromEmail(login: Login): Flow<DataState<User>>
}