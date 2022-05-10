package co.nimble.lee.assignment.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.model.toUserUiModels
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.ui.base.NavigationEvent
import co.nimble.lee.assignment.ui.screens.second.SecondBundle
import co.nimble.lee.assignment.domain.usecase.GetUsersUseCase
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val userUiModels: StateFlow<List<UserUiModel>>

    fun navigateToSecond(bundle: SecondBundle)

    fun navigateToCompose()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val logOutUseCase: LogOutUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    private val _userUiModels = MutableStateFlow<List<UserUiModel>>(emptyList())
    override val userUiModels: StateFlow<List<UserUiModel>>
        get() = _userUiModels

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit>
        get() = _logoutEvent

    init {
        fetchUsers()
    }

    override fun navigateToSecond(bundle: SecondBundle) {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Second(bundle))
        }
    }

    override fun navigateToCompose() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Compose)
        }
    }

    private fun fetchUsers() {
        showLoading()
        execute {
            when (val result = getUsersUseCase.execute()) {
                is UseCaseResult.Success -> _userUiModels.value = result.data.toUserUiModels()
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        }
    }

    fun logout() {
        viewModelScope.launch {
            logOutUseCase.invoke(Unit)
            _logoutEvent.emit(Unit)
        }
    }
}
