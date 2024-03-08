package com.sisco.typicode.domain.model

import com.sisco.typicode.data.source.local.UserEntity

data class User(
    val username: String,
    val email: String?,
    val password: String?,
    val role: String?
) {
    fun toEntity(): UserEntity = UserEntity(
        username = username,
        email = email,
        password = password,
        role = role
    )
}

data class Login(
    val email: String?,
    val password: String?
)
