package com.sisco.typicode.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    fun getUserFromEmail(email: String, password: String): UserEntity

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): UserEntity

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}