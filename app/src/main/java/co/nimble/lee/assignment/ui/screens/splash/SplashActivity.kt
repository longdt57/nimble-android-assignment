package co.nimble.lee.assignment.ui.screens.splash

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.nimble.lee.assignment.ui.screens.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    val viewModel by viewModels<SplashViewModel>()
}
