package co.nimble.lee.assignment.data.response.base

import com.squareup.moshi.Json

open class MetaItem<M>(

    @Json(name = "meta")
    val meta: M? = null
)
