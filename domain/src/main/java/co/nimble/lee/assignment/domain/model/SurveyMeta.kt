package co.nimble.lee.assignment.domain.model
data class SurveyMeta(
    val page: Int,
    val pageSize: Int,
    val pages: Int,
    val records: Int
) {
    fun getPageNumberAndSize(loadedSize: Int): Pair<Int, Int> {
        val number = loadedSize / pageSize + 1
        val size = this.pageSize - loadedSize % pageSize
        return Pair(number, size)
    }
}