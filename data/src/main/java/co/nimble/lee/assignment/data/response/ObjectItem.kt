package co.nimble.lee.assignment.data.response

import com.squareup.moshi.Json

open class ObjectItem<T> {

    @Json(name = "data")
    var data: T? = null
}
