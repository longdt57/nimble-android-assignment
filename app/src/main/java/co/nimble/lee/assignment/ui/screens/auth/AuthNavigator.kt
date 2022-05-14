package co.nimble.lee.assignment.ui.screens.auth

import androidx.fragment.app.Fragment
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.ui.base.BaseNavigator
import co.nimble.lee.assignment.ui.base.BaseNavigatorImpl
import co.nimble.lee.assignment.ui.base.NavigationEvent
import javax.inject.Inject

interface AuthNavigator : BaseNavigator

class AuthNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), AuthNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.ForgotPassword -> navigateToForgotPasswordScreen()
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToForgotPasswordScreen() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.signInFragment -> navController.navigate(
                SignInFragmentDirections.actionSignInToForgotPasswordFragment()
            )
            else -> unsupportedNavigation()
        }
    }
}
