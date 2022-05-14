package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.response.toUser
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUser(): User {
        return apiService.getUser().data!!.toUser()
    }
}
