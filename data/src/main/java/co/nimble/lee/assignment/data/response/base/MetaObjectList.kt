package co.nimble.lee.assignment.data.response.base

import com.squareup.moshi.Json

class MetaObjectList<T, M>(

    @Json(name = "data")
    val data: List<T>? = null,

    @Json(name = "meta")
    val meta: M? = null
)
