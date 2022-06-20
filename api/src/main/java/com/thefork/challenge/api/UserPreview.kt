package com.thefork.challenge.api

import com.thefork.challenge.domain.User

data class UserPreview(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String
)

fun UserPreview.toDomain(): User {
    return User(
        id = id,
        title = title,
        firstName = firstName,
        lastName = lastName,
        picture = picture
    )
}