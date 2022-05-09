package co.nimble.lee.assignment.ui.screens.authen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimble.lee.assignment.databinding.NbFragmentSignInBinding
import co.nimble.lee.assignment.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<NbFragmentSignInBinding>() {

    private val viewModel by viewModels<SignInViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> NbFragmentSignInBinding
        get() = { inflater, container, attachToParent ->
            NbFragmentSignInBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
    }

    override fun bindViewModel() {}
}
