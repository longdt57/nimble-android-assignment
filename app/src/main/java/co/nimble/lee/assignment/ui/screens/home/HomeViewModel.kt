package co.nimble.lee.assignment.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimble.lee.assignment.domain.usecase.GetSurveyUseCase
import co.nimble.lee.assignment.domain.usecase.GetUserUseCase
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.ui.base.NavigationEvent
import co.nimble.lee.assignment.ui.screens.second.SecondBundle
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.model.toSurveyUiModel
import co.nimble.lee.assignment.model.toUserUiModel
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val surveyUiModels: StateFlow<List<SurveyUIModel>>
    val userUiModel: SharedFlow<UserUiModel>

    fun navigateToSecond(bundle: SecondBundle)

    fun navigateToCompose()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSurveyUseCase: GetSurveyUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    private val _surveyUiModels = MutableStateFlow<List<SurveyUIModel>>(emptyList())
    override val surveyUiModels: StateFlow<List<SurveyUIModel>>
        get() = _surveyUiModels

    private val _userUiModel = MutableSharedFlow<UserUiModel>()
    override val userUiModel: SharedFlow<UserUiModel>
        get() = _userUiModel

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit>
        get() = _logoutEvent

    init {
        getSurveys()
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

    private fun getSurveys() {
        showLoading()
        execute {
            when (val result = getSurveyUseCase.invoke(GetSurveyUseCase.Param())) {
                is UseCaseResult.Success -> _surveyUiModels.value = result.data.toSurveyUiModel()
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()

            getUserUseCase()
        }
    }

    private fun getUserUseCase() {
        execute {
            when (val result = getUserUseCase.execute()) {
                is UseCaseResult.Success -> _userUiModel.emit(result.data.toUserUiModel())
                is UseCaseResult.Error -> {
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logOutUseCase.invoke(Unit)
            _logoutEvent.emit(Unit)
        }
    }
}
