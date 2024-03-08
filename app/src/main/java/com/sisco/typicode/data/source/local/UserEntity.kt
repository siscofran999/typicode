package com.sisco.typicode.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String?,
    val email: String?,
    val password: String?,
    val role: String?
) {
}