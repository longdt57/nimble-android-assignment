package co.nimble.lee.assignment.domain.repository

import co.nimble.lee.assignment.domain.model.Survey

interface SurveyRepository {
    suspend fun getSurvey(pageNumber: Int, pageSize: Int): List<Survey>
}