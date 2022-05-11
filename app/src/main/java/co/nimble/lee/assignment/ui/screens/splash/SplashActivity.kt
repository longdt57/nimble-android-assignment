package co.nimble.lee.assignment.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.ActivitySplashBinding
import co.nimble.lee.assignment.ui.base.BaseActivity
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.ext.navigateToMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val viewModel by viewModels<SplashViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = {
            ActivitySplashBinding.inflate(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogoAnimation()
    }

    private fun startLogoAnimation() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.nb_anim_alpha).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) = Unit
                override fun onAnimationRepeat(animation: Animation?) = Unit

                override fun onAnimationEnd(animation: Animation?) {
                    navigateToAuthenticationOrHome(viewModel.isLoggedIn())
                }

            })
        }
        binding.ivLog.startAnimation(anim)
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
