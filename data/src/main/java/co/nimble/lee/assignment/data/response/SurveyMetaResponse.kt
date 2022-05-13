package co.nimble.lee.assignment.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurveyMetaResponse(
    @Json(name = "page")
    val page: Int? = null,
    @Json(name = "page_size")
    val pageSize: Int? = null,
    @Json(name = "pages")
    val pages: Int? = null,
    @Json(name = "records")
    val records: Int? = null
)