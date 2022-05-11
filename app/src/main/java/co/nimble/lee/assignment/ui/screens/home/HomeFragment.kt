package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.databinding.ViewLoadingBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import co.nimblehq.common.extensions.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    private val surveyViewPager: ViewPager
        get() = binding.viewPager

    private val surveyAdapter: SurveyPagerAdapter by lazy {
        SurveyPagerAdapter { survey, position ->
            val isLastSurvey = position == surveyAdapter.count - 1
            if (isLastSurvey) {

            } else {
                surveyViewPager.setCurrentItem(position + 1, true)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        surveyViewPager.adapter = surveyAdapter
        binding.tabLayout.setupWithViewPager(surveyViewPager, true)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        with(binding) {
            btLogout.setOnSingleClickListener {
                viewModel.logout()
            }
        }
    }

    override fun bindViewModel() {
        viewModel.surveyUiModels bindTo ::displaySurveys
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
        viewModel.logoutEvent bindTo ::navigateToAuthentication
    }

    private fun displaySurveys(userUiModels: List<SurveyUIModel>) {
        surveyAdapter.submitList(userUiModels)
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
