package co.nimble.lee.assignment.data.response

import co.nimble.lee.assignment.domain.model.SurveyMeta
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurveyMetaResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "page_size")
    val pageSize: Int,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "records")
    val records: Int
)

fun SurveyMetaResponse.toSurveyMeta() = SurveyMeta(
    page = page,
    pageSize = pageSize,
    pages = pages,
    records = records
)