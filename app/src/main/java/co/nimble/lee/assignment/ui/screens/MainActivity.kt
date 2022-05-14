package co.nimble.lee.assignment.ui.screens

import android.view.LayoutInflater
import androidx.activity.viewModels
import co.nimble.lee.assignment.databinding.ActivityMainBinding
import co.nimble.lee.assignment.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override val viewModel by viewModels<MainViewModel>()
}
