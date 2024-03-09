package com.sisco.typicode.data.source.local

import com.sisco.typicode.utils.DataState
import com.sisco.typicode.domain.model.Login
import com.sisco.typicode.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val userDao: UserDao) {
    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    fun getUserFromEmail(login: Login): Flow<DataState<User>> = flow {
        val data = userDao.getUserFromEmail(login.email ?: "", login.password ?: "")
        if (data != null) {
            emit(DataState.Success(data.toDomain()))
        } else {
            emit(DataState.Error("error"))
        }
    }.catch { _ ->
        emit(DataState.Error("error"))
    }.flowOn(Dispatchers.IO)

    fun getUserForAdmin(email: String): Flow<List<User>> =
        userDao.getUserForAdmin(email).map { list ->
            list.map { it.toDomain() }
        }

    suspend fun deleteUser(userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }
}