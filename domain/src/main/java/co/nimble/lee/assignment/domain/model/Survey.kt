package co.nimble.lee.assignment.domain.model

data class Survey(
    val id: String,
    val title: String?,
    val description: String?,
    val coverImageUrl: String?,
    val type: String?,
    val thankEmailAboveThreshold: String?,
    val isActive: Boolean?,
    val createdAt: String?,
    val activeAt: String?,
    val inactiveAt: String?,
    val surveyType: String?
)