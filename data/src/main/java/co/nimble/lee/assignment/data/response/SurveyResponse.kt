package co.nimble.lee.assignment.data.response

import co.nimble.lee.assignment.domain.model.Survey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurveyResponse(
    @Json(name = "attributes")
    val attributes: Attributes? = null,
    @Json(name = "id")
    val id: String,
    @Json(name = "relationships")
    val relationships: Relationships? = null,
    @Json(name = "type")
    val type: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Attributes(
        @Json(name = "active_at")
        val activeAt: String? = null,
        @Json(name = "cover_image_url")
        val coverImageUrl: String? = null,
        @Json(name = "created_at")
        val createdAt: String? = null,
        @Json(name = "description")
        val description: String? = null,
        @Json(name = "inactive_at")
        val inactiveAt: String? = null,
        @Json(name = "is_active")
        val isActive: Boolean? = null,
        @Json(name = "survey_type")
        val surveyType: String? = null,
        @Json(name = "thank_email_above_threshold")
        val thankEmailAboveThreshold: String? = null,
        @Json(name = "thank_email_below_threshold")
        val thankEmailBelowThreshold: String? = null,
        @Json(name = "title")
        val title: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Relationships(
        @Json(name = "questions")
        val questions: Questions? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Questions(
            @Json(name = "data")
            val `data`: List<Data?>? = null
        ) {
            @JsonClass(generateAdapter = true)
            data class Data(
                @Json(name = "id")
                val id: String? = null,
                @Json(name = "type")
                val type: String? = null
            )
        }
    }
}

fun SurveyResponse.toSurvey(): Survey {
    return Survey(
        id = id,
        title = attributes?.title.orEmpty(),
        description = attributes?.description.orEmpty(),
        coverImageUrl = attributes?.coverImageUrl.orEmpty(),
        type = type,
        thankEmailAboveThreshold = attributes?.thankEmailAboveThreshold,
        isActive = attributes?.isActive,
        createdAt = attributes?.createdAt,
        activeAt = attributes?.activeAt,
        inactiveAt = attributes?.inactiveAt,
        surveyType = attributes?.surveyType
    )
}