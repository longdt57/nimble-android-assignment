package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): UseCaseResult<List<User>> {
        return try {
            val response = userRepository.getUsers()
            UseCaseResult.Success(response)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}
