package co.nimblehq.coroutine.di

import co.nimble.lee.assignment.di.modules.RepositoryModule
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.repository.UserRepository
import co.nimblehq.coroutine.mockrepository.MockLogoutRepository
import co.nimblehq.coroutine.mockrepository.MockOAuthRepository
import co.nimblehq.coroutine.mockrepository.MockSurveyRepository
import co.nimblehq.coroutine.mockrepository.MockUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryModule {

    @Provides
    fun provideOAuthRepository(): OAuthRepository = MockOAuthRepository()

    @Provides
    fun provideUserRepository(): UserRepository = MockUserRepository()

    @Provides
    fun provideSurveyRepository(): SurveyRepository = MockSurveyRepository()

    @Provides
    fun provideLogoutRepository(): LogoutRepository = MockLogoutRepository()
}
