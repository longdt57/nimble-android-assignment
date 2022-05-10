package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.common.extensions.visibleOrGone
import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.databinding.ViewLoadingBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import co.nimble.lee.assignment.ui.screens.second.SecondBundle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        with(binding) {
            btNext.setOnClickListener {
                viewModel.navigateToSecond(SecondBundle("From home"))
            }

            btCompose.setOnClickListener {
                viewModel.navigateToCompose()
            }

            btLogout.setOnSingleClickListener {
                viewModel.logout()
            }
        }
    }

    override fun bindViewModel() {
        viewModel.userUiModels bindTo ::displayUsers
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
        viewModel.logoutEvent bindTo :: navigateToAuthentication
    }

    private fun displayUsers(userUiModels: List<UserUiModel>) {
        Timber.d("Result : $userUiModels")
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }


    private fun navigateToAuthentication(unit: Unit) {
        requireActivity().apply {
            navigateToAuthentication()
            finish()
        }
    }
}
