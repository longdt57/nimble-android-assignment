package co.nimble.lee.assignment.data.response

import co.nimble.lee.assignment.domain.model.SurveyMeta
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurveyMetaResponse(
    @Json(name = "page")
    val page: Int = PAGE_NUMBER,
    @Json(name = "page_size")
    val pageSize: Int = PAGE_SIZE,
    @Json(name = "pages")
    val pages: Int = PAGE_NUMBER,
    @Json(name = "records")
    val records: Int = PAGE_SIZE
)

const val PAGE_NUMBER = 1
const val PAGE_SIZE = 5

fun SurveyMetaResponse.toSurveyMeta() = SurveyMeta(
    page = page,
    pageSize = pageSize,
    pages = pages,
    records = records
)