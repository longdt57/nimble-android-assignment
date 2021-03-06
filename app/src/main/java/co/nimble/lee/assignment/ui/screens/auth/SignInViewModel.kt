package co.nimble.lee.assignment.ui.screens.auth

import co.nimble.lee.assignment.domain.usecase.SignInUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@HiltViewModel
class SignInViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val signInWithEmailUseCase: SignInUseCase
) : BaseViewModel(dispatchers) {

    private val _signInSuccess = MutableSharedFlow<Unit>()
    val signInSuccess: SharedFlow<Unit>
        get() = _signInSuccess

    internal fun signInWithEmail(email: String, password: String) {
        showLoading()
        execute {
            val param = SignInUseCase.Param(email = email, password = password)
            when (val result = signInWithEmailUseCase.invoke(parameters = param)) {
                is UseCaseResult.Success -> {
                    _signInSuccess.emit(Unit)
                }
                is UseCaseResult.Error -> {
                    _error.emit(result.exception.message.orEmpty())
                }
            }
            hideLoading()
        }
    }
}
