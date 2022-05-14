package co.nimblehq.coroutine.domain.usecase

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import co.nimble.lee.assignment.domain.usecase.GetSurveyUseCase
import co.nimble.lee.assignment.domain.usecase.UseCaseResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSurveyUseCaseTest {

    private lateinit var mockRepository: SurveyRepository
    private lateinit var usecase: GetSurveyUseCase

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

    private val surveyMeta = SurveyMeta(1, 1, 1, 1)

    @Before
    fun setup() {
        mockRepository = mockk()
        usecase = GetSurveyUseCase(mockRepository)
    }

    @Test
    fun `When calling request successfully, it returns success response and save to Local`() = runBlockingTest {
        val expected = Pair(listOf(survey), surveyMeta)
        coEvery { mockRepository.getSurveysRemote(any(), any()) } returns expected
        mockSuccess()

        usecase.invoke(GetSurveyUseCase.Param(1, 1)).run {
            (this as UseCaseResult.Success).data shouldBe expected
        }

        io.mockk.verify(exactly = 1) {
            runBlockingTest { mockRepository.saveToDatabase(listOf(survey)) }
        }
    }

    @Test
    fun `When calling request successfully and pageNumber 1, it clears Database`() = runBlockingTest {
        val expected = Pair(listOf(survey), surveyMeta)
        coEvery { mockRepository.getSurveysRemote(any(), any()) } returns expected
        mockSuccess()

        usecase.invoke(GetSurveyUseCase.Param(1, 1)).run {
            (this as UseCaseResult.Success).data shouldBe expected
        }

        io.mockk.verify(exactly = 1) {
            runBlockingTest { mockRepository.clearSurveyDatabase() }
        }
    }

    private fun mockSuccess() {
        coEvery { mockRepository.clearSurveyDatabase() } returns Unit
        coEvery { mockRepository.saveToDatabase(listOf(survey)) } returns Unit
    }

    @Test
    fun `When calling request failed, it returns wrapped error`() = runBlockingTest {
        val expected = Exception()
        coEvery { mockRepository.getSurveysRemote(any(), any()) } throws expected
        coEvery { mockRepository.getSurveysLocal() } throws expected

        usecase.invoke(GetSurveyUseCase.Param(1, 1)).run {
            (this as UseCaseResult.Error).exception shouldBe expected
        }
    }
}
