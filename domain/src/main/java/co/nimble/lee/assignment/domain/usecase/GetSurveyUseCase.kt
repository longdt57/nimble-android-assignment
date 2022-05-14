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
                .apply { onRemoteSuccess(param, this) }
        } catch (exception: Exception) {
            if (param.isFirstPage())
                Pair(surveyRepository.getSurveysLocal(), null)
            else throw exception
        }
    }

    private suspend fun onRemoteSuccess(param: Param, pair: Pair<List<Survey>, SurveyMeta>) {
        if (param.isFirstPage()) {
            surveyRepository.clearSurveyDatabase()
        }
        surveyRepository.saveToDatabase(pair.first)
    }

    data class Param(
        val pageNumber: Int = 1,
        val pageSize: Int = 5
    ) {
        fun isFirstPage(): Boolean = pageNumber == 1
    }

}
