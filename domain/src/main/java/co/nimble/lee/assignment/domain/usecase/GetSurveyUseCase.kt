package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.model.SurveyMeta
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject

class GetSurveyUseCase @Inject constructor(private val surveyRepository: SurveyRepository) :
    BaseUseCase<GetSurveyUseCase.Param, Pair<List<Survey>, SurveyMeta>>() {

    override suspend fun execute(param: Param): Pair<List<Survey>, SurveyMeta> {
        return surveyRepository.getSurveys(pageNumber = param.pageNumber, pageSize = param.pageSize)
    }

    data class Param(
        val pageNumber: Int = 1,
        val pageSize: Int = 5
    )

}
