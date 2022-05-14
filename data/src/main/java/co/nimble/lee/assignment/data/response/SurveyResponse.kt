package co.nimble.lee.assignment.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import co.nimble.lee.assignment.domain.model.Survey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class SurveyResponse(
    @Json(name = "id")
    @PrimaryKey
    var id: String,

    @Json(name = "type")
    var type: String? = null,

    @Json(name = "attributes")
    @Embedded
    var attributes: Attributes? = null

) {

    @Json(name = "relationships")
    @Ignore
    var relationships: Relationships? = null

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
        type = type,
        title = attributes?.title.orEmpty(),
        description = attributes?.description.orEmpty(),
        coverImageUrl = attributes?.coverImageUrl.orEmpty(),
        thankEmailAboveThreshold = attributes?.thankEmailAboveThreshold,
        isActive = attributes?.isActive,
        createdAt = attributes?.createdAt,
        activeAt = attributes?.activeAt,
        inactiveAt = attributes?.inactiveAt,
        surveyType = attributes?.surveyType
    )
}

fun Survey.toSurveyDTO() = SurveyResponse(
    id = id,
    type = type,
    attributes = SurveyResponse.Attributes(
        title = title,
        description = description,
        coverImageUrl = coverImageUrl,
        thankEmailAboveThreshold = thankEmailAboveThreshold,
        isActive = isActive,
        createdAt = createdAt,
        activeAt = activeAt,
        inactiveAt = inactiveAt,
        surveyType = surveyType
    )
)