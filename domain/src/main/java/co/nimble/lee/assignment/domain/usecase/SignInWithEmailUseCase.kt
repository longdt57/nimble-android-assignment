package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<SignInWithEmailUseCase.Param, TokenInfo>() {

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
