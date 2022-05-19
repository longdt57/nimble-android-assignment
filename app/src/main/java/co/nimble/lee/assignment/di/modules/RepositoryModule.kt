package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.data.repository.LogoutRepositoryImpl
import co.nimble.lee.assignment.data.repository.OAuthRepositoryImpl
import co.nimble.lee.assignment.data.repository.SurveyRepositoryImpl
import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.domain.repository.LogoutRepository
import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideOAuthRepository(
        repoImpl: OAuthRepositoryImpl
    ): OAuthRepository = repoImpl

    @Provides
    fun provideUserRepository(
        repoImpl: UserRepositoryImpl
    ): UserRepository = repoImpl

    @Provides
    fun provideSurveyRepository(
        repoImpl: SurveyRepositoryImpl
    ): SurveyRepository = repoImpl

    @Provides
    fun provideLogoutRepository(
        repoImpl: LogoutRepositoryImpl
    ): LogoutRepository = repoImpl
}
