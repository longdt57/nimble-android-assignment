package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.response.toUsers
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().toUsers()
    }
}
