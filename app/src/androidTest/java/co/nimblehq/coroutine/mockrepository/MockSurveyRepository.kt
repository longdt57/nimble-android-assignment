package co.nimblehq.coroutine.mockrepository

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository

class MockSurveyRepository : SurveyRepository {

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

    override suspend fun getSurveysRemote(pageNumber: Int, pageSize: Int): Pair<List<Survey>, SurveyMeta> {
        return Pair(listOf(survey), surveyMeta)
    }

    override suspend fun getSurveysLocal(): List<Survey> {
        return listOf(survey)
    }

    override suspend fun clearSurveyDatabase() {}

    override suspend fun saveToDatabase(items: List<Survey>) {}
}
