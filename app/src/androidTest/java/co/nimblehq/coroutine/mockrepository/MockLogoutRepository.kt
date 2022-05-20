package co.nimblehq.coroutine.mockrepository

import co.nimble.lee.assignment.domain.repository.LogoutRepository

class MockLogoutRepository: LogoutRepository {
    override suspend fun logout() {}
}
