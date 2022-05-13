package co.nimble.lee.assignment.model

import co.nimble.lee.assignment.domain.model.User

data class UserUiModel(
    val id: String? = null,
    val type: String? = null,
    val avatarUrl: String? = null,
    val email: String? = null,
    val name: String? = null
)

fun User.toUserUiModel(): UserUiModel =
    UserUiModel(id = id, name = name, avatarUrl = avatarUrl, email = email, type = type)
