package co.nimble.lee.assignment.ui.base

import co.nimble.lee.assignment.ui.screens.detail.SurveyDetailBundle

sealed class NavigationEvent {
    data class SurveyDetail(val bundle: SurveyDetailBundle) : NavigationEvent()
}
