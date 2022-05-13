package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.repository.OAuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val userRepository: OAuthRepository) :
    BaseUseCase<Unit, Unit>() {

    override suspend fun execute(param: Unit) {
        return userRepository.logout()
    }

}
