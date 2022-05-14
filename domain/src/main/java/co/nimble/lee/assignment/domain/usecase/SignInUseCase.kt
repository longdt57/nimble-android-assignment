package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: OAuthRepository
) : BaseUseCase<SignInUseCase.Param, TokenInfo>() {

    override suspend fun execute(param: Param): TokenInfo {
        return userRepository.signInWithEmail(param.email, param.password).apply {
            userRepository.saveAuthToken(this)
        }
    }

    data class Param(
        val email: String,
        val password: String
    )

}
