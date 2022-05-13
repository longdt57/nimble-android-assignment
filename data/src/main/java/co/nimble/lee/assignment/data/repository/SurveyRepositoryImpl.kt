package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.response.toSurvey
import co.nimble.lee.assignment.data.response.toSurveyMeta
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SurveyRepository {

    override suspend fun getSurveys(pageNumber: Int, pageSize: Int): Pair<List<Survey>, SurveyMeta> {
        return apiService.getSurveys(pageNumber, pageSize).let {
            Pair(it.data!!.map { it.toSurvey() }, it.meta!!.toSurveyMeta())
        }
    }
}