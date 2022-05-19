package co.nimble.lee.assignment.di.modules

import android.content.Context
import android.content.Intent
import co.nimble.lee.assignment.data.service.tokenhelper.LogoutServiceIntent
import co.nimble.lee.assignment.helper.LogoutService
import co.nimble.lee.assignment.util.DispatchersProvider
import co.nimble.lee.assignment.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }

    @Provides
    @LogoutServiceIntent
    fun provideLogoutServiceIntent(
        @ApplicationContext context: Context
    ): Intent = Intent(context, LogoutService::class.java)
}
