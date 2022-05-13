package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta

interface SurveyRepository {
    suspend fun getSurveys(pageNumber: Int, pageSize: Int): Pair<List<Survey>, SurveyMeta>
}