package co.nimblehq.coroutine.ui

import co.nimble.lee.assignment.util.DispatchersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestRule(
    val providerImplTest: DispatchersProvider = DispatchersProviderImplTest()
): TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(providerImplTest.main)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
