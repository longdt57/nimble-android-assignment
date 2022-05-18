package co.nimblehq.coroutine.ui.screens.home

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.model.TokenInfo
import co.nimble.lee.assignment.domain.model.User
import co.nimble.lee.assignment.domain.usecase.GetSurveyUseCase
import co.nimble.lee.assignment.domain.usecase.GetUserUseCase
import co.nimble.lee.assignment.domain.usecase.LogOutUseCase
import co.nimble.lee.assignment.domain.usecase.SignInUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import co.nimble.lee.assignment.model.SurveyUiModel
import co.nimble.lee.assignment.model.toSurveyUiModel
import co.nimble.lee.assignment.ui.screens.home.HomeViewModel
import co.nimblehq.coroutine.ui.CoroutineTestRule
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var mockSurveyUseCase: GetSurveyUseCase
    private lateinit var mockUserUseCase: GetUserUseCase
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val user = User(
        id = "1",
        name = "Lee",
        email = "longdt57@gmail.com"
    )

    private val survey = Survey(
        id = "d5de6a8f8f5f1cfe51bc",
        type = "survey",
        title = "Scarlett Bangkok",
        description = "We'd love ot hear from you!",
        thankEmailAboveThreshold = "<span style=\"font-family:arial,helvetica,sans-serif\"><span style=\"font-size:14px\">Dear {name},<br /><br />Thank you for visiting Scarlett Wine Bar &amp; Restaurant at Pullman Bangkok Hotel G &nbsp;and for taking the time to complete our guest feedback survey!<br /><br />Your feedback is very important to us and each survey is read individually by the management and owners shortly after it is sent. We discuss comments and suggestions at our daily meetings and use them to constantly improve our services.<br /><br />We would very much appreciate it if you could take a few more moments and review us on TripAdvisor regarding your recent visit. By <a href=\"https://www.tripadvisor.com/Restaurant_Review-g293916-d2629404-Reviews-Scarlett_Wine_Bar_Restaurant-Bangkok.html\">clicking here</a> you will be directed to our page.&nbsp;<br /><br />Thank you once again and we look forward to seeing you soon!<br /><br />The Team at Scarlett Wine Bar &amp; Restaurant&nbsp;</span></span><span style=\"font-family:arial,helvetica,sans-serif; font-size:14px\">Pullman Bangkok Hotel G</span>",
        isActive = true,
        coverImageUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_",
        createdAt = "2017-01-23T07:48:12.991Z",
        activeAt = "2015-10-08T07:04:00.000Z",
        inactiveAt = null,
        surveyType = "Restaurant"
    )

    private val surveyMeta = SurveyMeta(1, 5, 4, 20)

    @Before
    fun setup() {
        mockSurveyUseCase = mockk()
        mockUserUseCase = mockk()
        viewModel = HomeViewModel(
            mockSurveyUseCase,
            mockUserUseCase,
            mockk(),
            coroutineTestRule.providerImplTest
        )
    }

    @Test
    fun `When calling get surveys request, it returns success response`() =
        runTest(coroutineTestRule.providerImplTest.io) {
            val surveyResult = Pair(listOf(survey), surveyMeta)

            coEvery { mockUserUseCase.execute() } returns UseCaseResult.Success(user)
            coEvery { mockSurveyUseCase.invoke(any()) } returns UseCaseResult.Success(surveyResult)

            val result = mutableListOf<List<SurveyUiModel>>()
            val job = launch { viewModel.surveyUiModels.toList(result) }
            viewModel.getSurveys()

            result.size shouldBe 2
            result.last() shouldBe surveyResult.first.toSurveyUiModel()
            job.cancel()
        }

    @Test
    fun `When calling get surveys request, it returns fail response`() =
        runTest(coroutineTestRule.providerImplTest.io) {
            val errorMessage = "Lee handsome"
            coEvery { mockSurveyUseCase.invoke(any()) } returns UseCaseResult.Error(Throwable(message = errorMessage))

            val result = mutableListOf<String>()
            val job = launch { viewModel.error.toList(result) }
            viewModel.getSurveys()

            result.size shouldBe 1
            result.first() shouldBe errorMessage
            job.cancel()
        }

}
