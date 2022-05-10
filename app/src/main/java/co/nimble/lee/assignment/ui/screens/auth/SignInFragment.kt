package co.nimble.lee.assignment.ui.screens.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.NbFragmentSignInBinding
import co.nimble.lee.assignment.databinding.ViewLoadingBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.ext.navigateToMain
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import co.nimblehq.common.extensions.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<NbFragmentSignInBinding>() {

    private val viewModel: SignInViewModel by provideViewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> NbFragmentSignInBinding
        get() = { inflater, container, attachToParent ->
            NbFragmentSignInBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        startLogoAnim()

        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.btnLogin.setOnSingleClickListener {
            viewModel.signInWithEmail(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
        }

        binding.edtEmail.doAfterTextChanged {
            updateBtnLogin()
        }
        binding.edtPassword.doAfterTextChanged {
            updateBtnLogin()
        }
    }

    override fun bindViewModel() {
        viewModel.error bindTo toaster::display
        viewModel.signInSuccess bindTo ::navigateToMain
        viewModel.showLoading bindTo ::bindLoading
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }

    private fun navigateToMain(unit: Unit) {
        requireActivity().let {
            it.navigateToMain()
            it.finish()
        }
    }

    private fun updateBtnLogin() {
        binding.btnLogin.isEnabled =
            binding.edtEmail.text.isNullOrBlank().not() && binding.edtPassword.text.isNullOrBlank().not()
    }

    private fun startLogoAnim() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.sign_in_logo_anim)
        binding.ivLogo.startAnimation(anim)
    }
}
