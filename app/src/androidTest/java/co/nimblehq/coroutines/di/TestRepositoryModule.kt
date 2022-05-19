package co.nimblehq.coroutines.di

import co.nimble.lee.assignment.di.modules.RepositoryModule
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.repository.UserRepository
import co.nimblehq.coroutines.mockrepository.MockLogoutRepository
import co.nimblehq.coroutines.mockrepository.MockOAuthRepository
import co.nimblehq.coroutines.mockrepository.MockSurveyRepository
import co.nimblehq.coroutines.mockrepository.MockUserRepository
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
