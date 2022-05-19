package co.nimblehq.coroutines.mockrepository

import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.repository.UserRepository

class MockUserRepository: UserRepository {

    val result = User(
        id = "1",
        type = "normal",
        name = "Lee Do",
        email = "abc@gmail.com",
        avatarUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_",
    )

    override suspend fun getUser(): User {
        return result
    }
}
