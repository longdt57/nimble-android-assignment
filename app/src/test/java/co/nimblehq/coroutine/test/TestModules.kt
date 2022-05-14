package co.nimblehq.coroutine.test

import co.nimble.lee.assignment.di.modules.NavigatorModule
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.auth.AuthNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [FragmentComponent::class],
    replaces = [NavigatorModule::class]
)
object TestNavigatorModule {
    val mockMainNavigator = mockk<MainNavigator>()
    val mockAuthNavigator = mockk<AuthNavigator>()

    @Provides
    fun provideMainNavigator() = mockMainNavigator

    @Provides
    fun provideAuthNavigator() = mockAuthNavigator
}
