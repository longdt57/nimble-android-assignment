package co.nimble.lee.assignment.ui.screens.auth

import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
) : BaseViewModel(dispatchers) {

    private val _signInSuccess = MutableSharedFlow<Unit>()
    val signInSuccess: SharedFlow<Unit>
        get() = _signInSuccess

    internal fun signInWithEmail(email: String, password: String) {
    }
}
