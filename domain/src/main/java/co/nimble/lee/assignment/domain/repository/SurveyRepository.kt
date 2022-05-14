package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta

interface SurveyRepository {
    suspend fun getSurveysRemote(pageNumber: Int, pageSize: Int): Pair<List<Survey>, SurveyMeta>
    suspend fun getSurveysLocal(): List<Survey>
    suspend fun clearSurveyDatabase()
    suspend fun saveToDatabase(items: List<Survey>)
}