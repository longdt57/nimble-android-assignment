package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.repository.LogoutRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: LogoutRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(param: Unit) {
        repository.logout()
    }

}
