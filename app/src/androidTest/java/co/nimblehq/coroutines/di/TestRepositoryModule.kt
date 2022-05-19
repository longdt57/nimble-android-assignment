package co.nimblehq.coroutines.di

import co.nimble.lee.assignment.di.modules.RepositoryModule
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryModule {

    @Provides
    fun provideOAuthRepository(): OAuthRepository = mockk()

    @Provides
    fun provideUserRepository(): UserRepository = mockk()

    @Provides
    fun provideSurveyRepository(): SurveyRepository = mockk()

    @Provides
    fun provideLogoutRepository(): LogoutRepository = mockk()
}
