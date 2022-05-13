package co.nimble.lee.assignment.data.response.base

import com.squareup.moshi.Json

open class ObjectItem<T>(

    @Json(name = "data")
    val data: T? = null
)
