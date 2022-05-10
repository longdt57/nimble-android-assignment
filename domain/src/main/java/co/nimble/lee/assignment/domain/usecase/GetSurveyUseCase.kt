package co.nimble.lee.assignment.domain.usecase

import co.nimble.lee.assignment.domain.model.Survey
import co.nimble.lee.assignment.domain.repository.SurveyRepository
import javax.inject.Inject

class GetSurveyUseCase @Inject constructor(private val surveyRepository: SurveyRepository) :
    BaseUseCase<GetSurveyUseCase.Param, List<Survey>>() {

    override suspend fun execute(param: Param): List<Survey> {
        return surveyRepository.getSurvey(pageNumber = param.pageNumber, pageSize = param.pageSize)
    }

    data class Param(
        val pageNumber: Int = 1,
        val pageSize: Int = 5
    )

}
