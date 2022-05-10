package co.nimble.lee.assignment.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class ObjectList<T>(
    @Json(name = "data")
    open val data: List<T>? = null
)