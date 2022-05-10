package co.nimble.lee.assignment.ui.screens.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.ext.navigateToMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            navigateToAuthenticationOrHome(viewModel.isLoggedIn())
        }
    }

    private fun navigateToAuthenticationOrHome(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            navigateToMain()
        } else {
            navigateToAuthentication()
        }
        finish()
    }
}
