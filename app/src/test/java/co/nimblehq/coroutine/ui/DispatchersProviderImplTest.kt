package co.nimblehq.coroutine.ui

import co.nimble.lee.assignment.util.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class DispatchersProviderImplTest(private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()) : DispatchersProvider {

    override val io: CoroutineDispatcher
        get() = dispatcher
    override val main: CoroutineDispatcher
        get() = dispatcher
    override val default: CoroutineDispatcher
        get() = dispatcher
}