package co.nimble.lee.assignment.model

import android.os.Parcelable
import co.nimble.lee.assignment.domain.model.Survey
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyUiModel(
    val id: String,
    val title: String?,
    val description: String?,
    val coverImageUrl: String?
) : Parcelable

fun Survey.toSurveyUiModel() = SurveyUiModel(
    id = id,
    title = title,
    description = description,
    coverImageUrl = coverImageUrl
)

fun List<Survey>.toSurveyUiModel() = map { it.toSurveyUiModel() }
