package co.nimble.lee.assignment.ui.screens.splash

import co.nimble.lee.assignment.domain.repository.OAuthRepository
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val userRepository: OAuthRepository
) : BaseViewModel(dispatchers) {

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}
