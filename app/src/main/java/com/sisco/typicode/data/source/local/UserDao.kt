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
    suspend fun insertUser()

    @Query("SELECT * FROM user_table")
    suspend fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserFromEmail(email: String): Flow<UserEntity>

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}