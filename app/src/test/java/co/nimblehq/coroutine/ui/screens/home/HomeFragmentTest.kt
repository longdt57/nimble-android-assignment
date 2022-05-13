package co.nimblehq.coroutine.ui.screens.home

import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.test.TestNavigatorModule.mockMainNavigator
import co.nimblehq.coroutine.test.getPrivateProperty
import co.nimblehq.coroutine.test.replace
import co.nimble.lee.assignment.ui.BaseFragmentTest
import co.nimble.lee.assignment.ui.screens.ext.getDateTimeEEMMdd
import co.nimble.lee.assignment.ui.screens.home.HomeFragment
import co.nimble.lee.assignment.ui.screens.home.HomeViewModel
import dagger.hilt.android.testing.*
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.*

@HiltAndroidTest
class HomeFragmentTest : BaseFragmentTest<HomeFragment, FragmentHomeBinding>() {

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing HomeFragment, its views display the text correctly`() {
        launchFragment()
        fragment.binding.tvHomeTitle.text.toString() shouldBe "Today"
        fragment.binding.tvDateTime.text.toString() shouldBe getDateTimeEEMMdd(System.currentTimeMillis())
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
            }
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
        }
    }
}
