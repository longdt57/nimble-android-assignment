package co.nimble.lee.assignment.ui.screens.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentForgotPasswordBinding
import co.nimble.lee.assignment.databinding.ViewLoadingBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.ext.hideKeyboard
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import co.nimblehq.common.extensions.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    @Inject
    lateinit var navigator: AuthNavigator

    private val viewModel: ForgotPasswordViewModel by provideViewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotPasswordBinding
        get() = { inflater, container, attachToParent ->
            FragmentForgotPasswordBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.btnDone.setOnSingleClickListener {
            forgotPassword()
        }

        setupEdtEmail()
    }

    override fun bindViewModel() {
        viewModel.error bindTo toaster::display
        viewModel.requestSuccess bindTo ::showSuccessAlert
        viewModel.showLoading bindTo ::bindLoading
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }

    private fun showSuccessAlert(message: String) {
        val dialogMessage =
            message.takeUnless { it.isNullOrBlank() } ?: getString(R.string.forgot_password_dialog_message)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.success)
            .setMessage(dialogMessage)
            .setPositiveButton(R.string.ok) { _, _ ->
                navigator.navigateUp()
            }
            .show()
    }

    private fun updateBtnLogin() {
        binding.btnDone.isEnabled = binding.edtEmail.text.isNullOrBlank().not()
    }

    private fun setupEdtEmail() {
        binding.edtEmail.apply {
            doAfterTextChanged {
                updateBtnLogin()
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == IME_ACTION_DONE) {
                    hideKeyboard(requireContext())
                    forgotPassword()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun forgotPassword() {
        binding.edtEmail.hideKeyboard(requireContext())
        viewModel.forgotPassword(binding.edtEmail.text.toString())
    }

}
