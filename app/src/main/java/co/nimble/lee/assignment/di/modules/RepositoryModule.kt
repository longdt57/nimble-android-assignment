package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.data.repository.UserRepositoryImpl
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserRepository(apiService: ApiService): UserRepository =
        UserRepositoryImpl(apiService)
}
