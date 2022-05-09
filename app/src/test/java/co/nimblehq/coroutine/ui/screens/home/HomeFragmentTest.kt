package co.nimble.lee.assignment.ui.screens.home

import co.nimble.lee.assignment.databinding.FragmentHomeBinding
import co.nimble.lee.assignment.test.TestNavigatorModule.mockMainNavigator
import co.nimble.lee.assignment.test.getPrivateProperty
import co.nimble.lee.assignment.test.replace
import co.nimble.lee.assignment.ui.BaseFragmentTest
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
        fragment.binding.btNext.text.toString() shouldBe "Next"
        fragment.binding.btCompose.text.toString() shouldBe "Jetpack Compose"
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
