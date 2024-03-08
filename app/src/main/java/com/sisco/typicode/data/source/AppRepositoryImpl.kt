package com.sisco.typicode.data.source

import com.sisco.typicode.utils.DataState
import com.sisco.typicode.data.source.local.LocalDataSource
import com.sisco.typicode.domain.model.Login
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : AppRepository {
    override suspend fun insertUser(user: User) {
        localDataSource.insertUser(user.toEntity())
    }

    override fun getUserFromEmail(login: Login): Flow<DataState<User>> =
        localDataSource.getUserFromEmail(login)

    override fun getUserForAdmin(email: String): Flow<List<User>> =
        localDataSource.getUserForAdmin(email)
}