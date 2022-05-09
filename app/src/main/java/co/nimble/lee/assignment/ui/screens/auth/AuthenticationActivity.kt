package co.nimble.lee.assignment.ui.screens.auth

import android.view.LayoutInflater
import androidx.activity.viewModels
import co.nimble.lee.assignment.databinding.ActivityAuthenBinding
import co.nimble.lee.assignment.ui.base.BaseActivity
import co.nimble.lee.assignment.ui.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityAuthenBinding
        get() = { inflater -> ActivityAuthenBinding.inflate(inflater) }

    override val viewModel by viewModels<MainViewModel>()
}
