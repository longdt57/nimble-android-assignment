package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.response.toSurvey
import co.nimble.lee.assignment.data.service.AuthenticatedApiService
import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyRepositoryImpl @Inject constructor(
    private val authenticatedApiService: AuthenticatedApiService
) : SurveyRepository {

    override suspend fun getSurvey(pageNumber: Int, pageSize: Int): List<Survey> {
        return authenticatedApiService.getSurveyList(pageNumber, pageSize).data!!.map { it.toSurvey() }
    }
}