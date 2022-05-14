package co.nimble.lee.assignment.data.repository

import co.nimble.lee.assignment.data.database.SurveyDao
import co.nimble.lee.assignment.data.response.toSurvey
import co.nimble.lee.assignment.data.response.toSurveyDTO
import co.nimble.lee.assignment.data.response.toSurveyMeta
import co.nimble.lee.assignment.data.service.ApiService
import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val surveyDao: SurveyDao
) : SurveyRepository {

    override suspend fun getSurveysRemote(pageNumber: Int, pageSize: Int): Pair<List<Survey>, SurveyMeta> {
        return apiService.getSurveys(pageNumber, pageSize).let {
            Pair(it.data!!.map { it.toSurvey() }, it.meta!!.toSurveyMeta())
        }
    }

    override suspend fun getSurveysLocal() = surveyDao.getAll().map { it.toSurvey() }

    override suspend fun saveToDatabase(items: List<Survey>) {
        surveyDao.insertAll(items.map { it.toSurveyDTO() })
    }

    override suspend fun clearSurveyDatabase() = surveyDao.clear()
}