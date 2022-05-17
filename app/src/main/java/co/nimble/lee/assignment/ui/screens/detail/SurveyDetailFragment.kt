package co.nimble.lee.assignment.ui.screens.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimble.lee.assignment.databinding.FragmentSurveyDetailBinding
import co.nimble.lee.assignment.extension.provideNavArgs
import co.nimble.lee.assignment.extension.provideViewModels
import co.nimble.lee.assignment.model.SurveyUiModel
import co.nimble.lee.assignment.ui.base.BaseToolbarFragment
import co.nimble.lee.assignment.ui.screens.MainNavigator
import co.nimble.lee.assignment.ui.screens.ext.loadSurveyCoverImage
import co.nimble.lee.assignment.ui.screens.ext.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SurveyDetailFragment : BaseToolbarFragment<FragmentSurveyDetailBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by provideViewModels<SurveyDetailViewModel>()

    private val args: SurveyDetailFragmentArgs by provideNavArgs()

    private val surveyUiModel: SurveyUiModel
        get() = args.bundle.model

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSurveyDetailBinding
        get() = FragmentSurveyDetailBinding::inflate

    override fun setupView() {
        binding.tvTitle.text = surveyUiModel.title
        binding.tvDescription.text = surveyUiModel.description
        binding.ivCover.loadSurveyCoverImage(surveyUiModel.coverImageUrl.orEmpty())
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.btnSurvey.setOnSingleClickListener {
            toaster.display("Not Implemented Yet")
        }
    }

    override fun bindViewModel() {}
}
