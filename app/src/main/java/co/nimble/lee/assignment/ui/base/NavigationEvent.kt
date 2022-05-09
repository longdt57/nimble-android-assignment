package co.nimble.lee.assignment.ui.base

import co.nimble.lee.assignment.ui.screens.second.SecondBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
    object Compose : NavigationEvent()
}
