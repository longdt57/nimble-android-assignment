package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.User

interface UserRepository {

    suspend fun getUser(): User
}
