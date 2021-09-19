package dev.burnoo.cokoin.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.utils.BaseTest
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module

private class TestNavViewModel : ViewModel() {

    val counterText = (++counter).toString()

    companion object {
        var counter = 0
    }
}

class NavigationTest : BaseTest() {

    @Test
    fun useSameViewModelInstanceInNavigation() {
        val module = module {
            viewModel { TestNavViewModel() }
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            Koin(koinApplication = koinApplication {
                modules(module)
            }) {
                KoinNavHost(navController, startDestination = "1") {
                    composable("1") {
                        Button(onClick = { navController.navigate("2") }) {
                            Text("go to 2")
                        }
                        val navViewModel = getNavViewModel<TestNavViewModel>()
                        Text(text = navViewModel.counterText)
                    }
                    composable("2") {
                        Button(onClick = { navController.navigate("1") }) {
                            Text("go to 1")
                        }
                        val navViewModel = getNavViewModel<TestNavViewModel>()
                        Text(text = navViewModel.counterText)
                    }
                }
            }
        }

        assertText("1", index = 1)

        clickOnText("go to 2")
        assertText("1", index = 1)

        clickOnText("go to 1")
        assertText("1", index = 1)
    }

    @Test
    fun useGetNavController() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Koin {
                KoinNav(navController = navController) {
                    val navControllerFromKoin = getNavController()

                    Text(text = (navController === navControllerFromKoin).toString())
                }
            }
        }

        assertText("true")
    }

    private fun clickOnText(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }
}