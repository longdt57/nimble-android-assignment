package co.nimble.lee.assignment.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import co.nimble.lee.assignment.R
import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.lib.IsLoading
import co.nimble.lee.assignment.model.SurveyUiModel
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

    private var lastPosition: Int
        get() = viewModel.lastSelectedPosition
        set(value) {
            viewModel.lastSelectedPosition = value
        }

    private val surveyAdapter: SurveyPagerAdapter by lazy {
        SurveyPagerAdapter { survey, _ ->
            openSurveyDetailScreen(survey)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupView() {
        binding.tvDateTime.text = getDateTimeEEMMdd(System.currentTimeMillis())
        bindSkeleton()
        setupViewPager()
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.ivProfile.setOnSingleClickListener {
            viewModel.logout()
        }
        binding.btnTakeSurvey.setOnSingleClickListener {
            openSurveyDetailScreen(surveyAdapter.currentList[surveyViewPager.currentItem])
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

    private fun displayUser(userUiModel: UserUiModel?) {
        if (userUiModel == null) return
        Glide.with(requireContext())
            .load(userUiModel.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.nb_profile_place_holder)
            .error(R.drawable.nb_profile_place_holder)
            .into(binding.ivProfile)
    }

    private fun displaySurveys(userUiModels: List<SurveyUiModel>) {
        binding.tvState.isVisible = userUiModels.isNullOrEmpty()

        // Avoid resubmit the same data when fragment recreate view
        if (userUiModels == surveyAdapter.currentList) {
            return
        }

        surveyAdapter.submitList(userUiModels)
    }

    private fun bindLoading(isLoading: IsLoading) {
        isLoading.not().apply {
            binding.tvDateTime.isVisible = this
            binding.tvHomeTitle.isVisible = this
            binding.ivProfile.isVisible = this
            binding.btnTakeSurvey.isVisible = this
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

    private fun openSurveyDetailScreen(survey: SurveyUiModel) {
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
                    lastPosition = position
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

    private fun setupViewPager() {
        surveyViewPager.adapter = surveyAdapter
        TabLayoutMediator(binding.tabLayout, surveyViewPager) { _, _ -> }.attach()
        setLastSelectedSurvey()
    }

    private fun setLastSelectedSurvey() {
        if (lastPosition < surveyAdapter.currentList.size &&
            surveyViewPager.currentItem != lastPosition
        ) {
            surveyViewPager.setCurrentItem(lastPosition, false)
        }
    }
}
