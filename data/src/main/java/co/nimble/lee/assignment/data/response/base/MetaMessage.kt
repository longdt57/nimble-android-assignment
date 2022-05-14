package co.nimble.lee.assignment.data.response.base

import co.nimble.lee.assignment.domain.model.Message
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaMessage(
    @Json(name = "message")
    val message: String? = null
)

fun MetaMessage.toMessage() = Message(message)
