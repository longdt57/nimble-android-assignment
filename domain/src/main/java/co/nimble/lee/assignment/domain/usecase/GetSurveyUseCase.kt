package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject

class GetSurveyUseCase @Inject constructor(private val surveyRepository: SurveyRepository) :
    BaseUseCase<GetSurveyUseCase.Param, Pair<List<Survey>, SurveyMeta?>>() {

    override suspend fun execute(param: Param): Pair<List<Survey>, SurveyMeta?> {
        return try {
            return surveyRepository.getSurveysRemote(pageNumber = param.pageNumber, pageSize = param.pageSize)
                .apply {
                    clearLocalDatabaseIfFirstPage(param)
                    surveyRepository.saveToDatabase(first)
                }
        } catch (exception: Exception) {
            if (param.isFirstPage()) Pair(surveyRepository.getSurveysLocal(), null)
            else throw exception
        }
    }

    private suspend fun clearLocalDatabaseIfFirstPage(param: Param) {
        if (param.isFirstPage()) {
            surveyRepository.clearSurveyDatabase()
        }
    }

    class Param(
        val pageNumber: Int = 1,
        val pageSize: Int = 5
    ) {
        fun isFirstPage(): Boolean = pageNumber == 1
    }

}
