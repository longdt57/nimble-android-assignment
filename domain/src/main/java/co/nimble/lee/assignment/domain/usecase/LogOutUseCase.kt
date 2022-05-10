package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.repository.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<Unit, Unit>() {

    override suspend fun execute(param: Unit) {
        return userRepository.logout()
    }

}
