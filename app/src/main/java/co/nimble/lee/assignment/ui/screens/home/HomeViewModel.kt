package co.nimble.lee.assignment.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.usecase.GetSurveyUseCase
import co.nimble.lee.assignment.domain.usecase.GetUserUseCase
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.model.toSurveyUiModel
import co.nimble.lee.assignment.model.toUserUiModel
import co.nimble.lee.assignment.ui.base.BaseViewModel
import co.nimble.lee.assignment.ui.base.NavigationEvent
import co.nimble.lee.assignment.ui.screens.detail.SurveyDetailBundle
import co.nimble.lee.assignment.ui.screens.ext.orFalse
import co.nimble.lee.assignment.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface Output {

    var lastSelectedPosition: Int
    val surveyUiModels: StateFlow<List<SurveyUIModel>>
    val userUiModel: SharedFlow<UserUiModel>

    fun navigateToSurveyDetail(bundle: SurveyDetailBundle)
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

    private var surveyMeta: SurveyMeta? = null
    override var lastSelectedPosition: Int = 0

    init {
        getSurveys()
    }

    override fun navigateToSurveyDetail(bundle: SurveyDetailBundle) {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.SurveyDetail(bundle))
        }
    }

    fun getSurveys() {
        showLoading()
        execute {
            when (val result = getSurveyUseCase.invoke(GetSurveyUseCase.Param(shouldLoadDatabaseIfFail = true))) {
                is UseCaseResult.Success -> {
                    _surveyUiModels.value = result.data.first.toSurveyUiModel()
                    surveyMeta = result.data.second
                    getUser()
                }
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        }
    }

    fun loadMoreSurveys(loadedSize: Int) {
        if (surveyMeta?.canLoadMore(loadedSize).orFalse().not()) return
        val pageInfo = surveyMeta!!.getPageNumberAndSize(loadedSize)
        execute {
            val param = GetSurveyUseCase.Param(pageInfo.first, pageInfo.second, shouldLoadDatabaseIfFail = false)
            when (val result = getSurveyUseCase.invoke(parameters = param)) {
                is UseCaseResult.Success -> {
                    _surveyUiModels.apply {
                        value = value.toMutableList().apply {
                            addAll(result.data.first.toSurveyUiModel())
                        }
                    }
                    surveyMeta = result.data.second
                    getUser()
                }
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
        }
    }

    private fun getUser() {
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
