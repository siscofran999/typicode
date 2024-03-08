package com.sisco.typicode.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sisco.typicode.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val username: String,
    val email: String?,
    val password: String?,
    val role: String?
) {
    fun toDomain(): User = User(
        username = username,
        email = email,
        password = password,
        role = role
    )
}