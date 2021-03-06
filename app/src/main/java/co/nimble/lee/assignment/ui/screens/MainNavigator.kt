package co.nimble.lee.assignment.ui.screens

import androidx.fragment.app.Fragment
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.ui.base.BaseNavigator
import co.nimble.lee.assignment.ui.base.BaseNavigatorImpl
import co.nimble.lee.assignment.ui.base.NavigationEvent
import co.nimble.lee.assignment.ui.screens.detail.SurveyDetailBundle
import co.nimble.lee.assignment.ui.screens.home.HomeFragmentDirections
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.SurveyDetail -> navigateToSurveyDetail(event.bundle)
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToSurveyDetail(bundle: SurveyDetailBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeFragment -> navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToSecondFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }
}
