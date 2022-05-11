package co.nimble.lee.assignment.model

import co.nimble.lee.assignment.domain.model.Survey

data class SurveyUIModel(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String
)

fun Survey.toSurveyUiModel() = SurveyUIModel(
    id = id,
    title = title,
    description = description,
    coverImageUrl = coverImageUrl
)

fun List<Survey>.toSurveyUiModel() = map { it.toSurveyUiModel() }