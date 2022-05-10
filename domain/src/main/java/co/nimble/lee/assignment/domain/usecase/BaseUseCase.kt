package co.nimble.lee.assignment.domain.usecase

abstract class BaseUseCase<in I, out O> {

    /**
     * This is an internal function, don't call it directly.
     */
    protected abstract suspend fun execute(param: I): O

    suspend operator fun invoke(parameters: I): UseCaseResult<O> = try {
        UseCaseResult.Success(execute(parameters)!!)
    } catch (throwable: Throwable) {
        parseError(throwable)
    }

    protected fun parseError(throwable: Throwable): UseCaseResult.Error {
        return UseCaseResult.Error(throwable)
    }
}
