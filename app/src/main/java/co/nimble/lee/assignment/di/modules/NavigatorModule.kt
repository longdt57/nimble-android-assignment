package co.nimble.lee.assignment.di.modules

import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.MainNavigatorImpl
import co.nimble.lee.assignment.ui.screens.auth.AuthNavigator
import co.nimble.lee.assignment.ui.screens.auth.AuthNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun mainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator

    @Binds
    abstract fun authNavigator(mainNavigator: AuthNavigatorImpl): AuthNavigator
}
