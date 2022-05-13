package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.databinding.ViewLoadingBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.ext.getDateTimeEEMMdd
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.detail.SurveyDetailBundle
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import co.nimblehq.common.extensions.visibleOrGone
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    private lateinit var surveyAdapter: SurveyPagerAdapter

    private val surveyCallback: ((SurveyUIModel, Int) -> Unit)
        get() = { survey, _ ->
            openSurveyDetailScreen(survey)
        }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        binding.tvDateTime.text = getDateTimeEEMMdd(System.currentTimeMillis())
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.ivProfile.setOnSingleClickListener {
            viewModel.logout()
        }
    }

    override fun bindViewModel() {
        viewModel.surveyUiModels bindTo ::displaySurveys
        viewModel.userUiModel bindTo ::displayUser
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
        viewModel.logoutEvent bindTo ::navigateToAuthentication
    }

    private fun displayUser(userUiModel: UserUiModel) {
        Glide.with(requireContext())
            .load(userUiModel.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.nb_profile_place_holder)
            .into(binding.ivProfile)
    }

    private fun displaySurveys(userUiModels: List<SurveyUIModel>) {
        surveyAdapter = SurveyPagerAdapter(userUiModels, surveyCallback)
        surveyViewPager.adapter = surveyAdapter
        binding.tabLayout.setupWithViewPager(surveyViewPager, true)
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

    private fun openSurveyDetailScreen(survey: SurveyUIModel) {
        viewModel.navigateToSurveyDetail(SurveyDetailBundle(survey))
    }
}
