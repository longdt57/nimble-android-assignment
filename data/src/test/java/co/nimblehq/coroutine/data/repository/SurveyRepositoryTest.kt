package co.nimblehq.coroutine.data.repository

import co.nimble.lee.assignment.data.repository.SurveyRepositoryImpl
import co.nimble.lee.assignment.data.response.SurveyMetaResponse
import co.nimble.lee.assignment.data.response.SurveyResponse
import co.nimble.lee.assignment.data.response.base.MetaObjectList
import co.nimble.lee.assignment.data.response.toSurvey
import co.nimble.lee.assignment.data.response.toSurveyMeta
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SurveyRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var repository: SurveyRepository

    private val survey = SurveyResponse(
        id = "d5de6a8f8f5f1cfe51bc",
        type = "survey",
        attributes = SurveyResponse.Attributes(
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
    )

    private val surveyMeta = SurveyMetaResponse()

    @Before
    fun setup() {
        mockService = mockk()
        repository = SurveyRepositoryImpl(mockService)
    }

    @Test
    fun `When calling getUsers request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockService.getSurveys(1, 1) } returns MetaObjectList(listOf(survey), surveyMeta)

        repository.getSurveys(1, 1) shouldBe Pair(listOf(survey.toSurvey()), surveyMeta.toSurveyMeta())
    }

    @Test
    fun `When calling getUsers request failed, it returns wrapped error`() = runBlockingTest {
        coEvery { mockService.getSurveys(1, 1) } throws Throwable()

        shouldThrow<Throwable> {
            repository.getSurveys(1, 1)
        }
    }
}
