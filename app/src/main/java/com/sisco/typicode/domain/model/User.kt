package com.sisco.typicode.domain.model

import android.os.Parcelable
import com.sisco.typicode.data.source.local.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val username: String?,
    val email: String?,
    val password: String?,
    val role: String?
): Parcelable {
    fun toEntity(): UserEntity = UserEntity(
        id = id,
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
