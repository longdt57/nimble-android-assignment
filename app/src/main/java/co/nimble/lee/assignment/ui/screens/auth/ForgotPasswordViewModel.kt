package co.nimble.lee.assignment.ui.screens.auth

import co.nimble.lee.assignment.domain.usecase.ForgotPasswordUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel(dispatchers) {

    private val _requestSuccess = MutableSharedFlow<String>()
    val requestSuccess: SharedFlow<String>
        get() = _requestSuccess

    internal fun forgotPassword(email: String) {
        showLoading()
        execute {
            val param = ForgotPasswordUseCase.Param(email = email)
            when (val result = forgotPasswordUseCase.invoke(parameters = param)) {
                is UseCaseResult.Success -> {
                    _requestSuccess.emit(result.data?.message.orEmpty())
                }
                is UseCaseResult.Error -> {
                    _error.emit(result.exception.message.orEmpty())
                }
            }
            hideLoading()
        }
    }
}
