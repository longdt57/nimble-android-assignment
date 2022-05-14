package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.model.SurveyUIModel
import co.nimble.lee.assignment.model.UserUiModel
import co.nimble.lee.assignment.ui.base.BaseFragment
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.detail.SurveyDetailBundle
import co.nimble.lee.assignment.ui.screens.ext.getDateTimeEEMMdd
import co.nimble.lee.assignment.ui.screens.ext.navigateToAuthentication
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    private lateinit var skeleton: SkeletonScreen

    private val surveyViewPager: ViewPager2
        get() = binding.viewPager

    private val surveyAdapter: SurveyPagerAdapter by lazy {
        SurveyPagerAdapter { survey, _ ->
            openSurveyDetailScreen(survey)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.tvDateTime.text = getDateTimeEEMMdd(System.currentTimeMillis())
        bindSkeleton()
        surveyViewPager.adapter = surveyAdapter
        TabLayoutMediator(binding.tabLayout, surveyViewPager) { _, _ -> }.attach()
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.ivProfile.setOnSingleClickListener {
            viewModel.logout()
        }
        bindSwipeRefreshEvent()
        bindViewPageEvent()
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
        surveyAdapter.submitList(userUiModels)
    }

    private fun bindLoading(isLoading: IsLoading) {
        isLoading.not().apply {
            binding.tvDateTime.isVisible = this
            binding.tvHomeTitle.isVisible = this
            binding.ivProfile.isVisible = this
        }

        if (isLoading) {
            skeleton.show()
        } else {
            skeleton.hide()
        }
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

    private fun bindViewPageEvent() {
        surveyViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    binding.swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (surveyAdapter.needLoadMoreItem(position)) {
                        viewModel.loadMoreSurveys(surveyAdapter.itemCount)
                    }
                }
            }
        )
    }

    private fun bindSwipeRefreshEvent() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.getSurveys()
                isRefreshing = false
            }
        }
    }

    private fun bindSkeleton() {
        skeleton = Skeleton.bind(binding.viewPager)
            .load(R.layout.layout_home_skeleton)
            .shimmer(true)
            .angle(20)
            .show()
    }
}
