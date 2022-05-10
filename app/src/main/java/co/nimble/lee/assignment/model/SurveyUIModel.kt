package co.nimble.lee.assignment.model

import co.nimble.lee.assignment.domain.model.Survey

data class SurveyUIModel(val id: String)

fun Survey.toSurveyUiModel() = SurveyUIModel(id = id)
fun List<Survey>.toSurveyUiModel() = map { it.toSurveyUiModel() }