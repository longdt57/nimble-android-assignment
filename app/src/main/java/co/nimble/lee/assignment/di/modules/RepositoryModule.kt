package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.data.repository.SurveyRepositoryImpl
import co.nimble.lee.assignment.data.repository.OAuthRepositoryImpl
import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.data.service.OAuthApiService
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.repository.OAuthRepository
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
        apiService: OAuthApiService,
        tokenStorage: TokenStorage
    ): OAuthRepository = OAuthRepositoryImpl(apiService, tokenStorage)

    @Provides
    fun provideUserRepository(
        apiService: ApiService,
    ): UserRepository = UserRepositoryImpl(apiService)

    @Provides
    fun provideSurveyRepository(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository = surveyRepositoryImpl
}
