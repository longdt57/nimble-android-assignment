package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.Message
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val userRepository: OAuthRepository
) : BaseUseCase<ForgotPasswordUseCase.Param, Message?>() {

    override suspend fun execute(param: Param): Message? {
        return userRepository.forgotPassword(param.email)
    }

    data class Param(
        val email: String
    )
}
