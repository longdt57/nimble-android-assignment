package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.data.repository.SurveyRepositoryImpl
import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.data.service.AuthenticatedApiService
import co.nimble.lee.assignment.data.storage.local.TokenStorage
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
    fun provideUserRepository(
        apiService: ApiService,
        authenticatedApiService: AuthenticatedApiService,
        tokenStorage: TokenStorage
    ): UserRepository =
        UserRepositoryImpl(apiService, authenticatedApiService, tokenStorage)

    @Provides
    fun provideSurveyRepository(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository = surveyRepositoryImpl
}
