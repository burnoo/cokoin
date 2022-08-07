package dev.burnoo.cokoin.viewmodel

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.burnoo.cokoin.Koin
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private class TestViewModel(
    val text: String = "ViewModelText",
    val handle: SavedStateHandle? = null
) : ViewModel() {

    val counterText = (++counter).toString()

    companion object {
        var counter = 0
    }
}

class ViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun getViewModelFromKoinModule() {
        val module = module {
            viewModel { TestViewModel() }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val viewModel = getViewModel<TestViewModel>()
                Text(viewModel.text)
            }
        }

        assertText("ViewModelText")
    }

    @Test
    fun getViewModelWithParametersFromKoinModule() {
        val module = module {
            viewModel { TestViewModel(text = get()) }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val viewModel =
                    getViewModel<TestViewModel>(parameters = {
                        parametersOf("ParameterText")
                    })
                Text(viewModel.text)
            }
        }

        assertText("ParameterText")
    }

    @Test
    fun getViewModelWithSavedStateHandle() {
        val module = module {
            viewModel { TestViewModel(handle = get()) }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val viewModel = getViewModel<TestViewModel>()
                Text((viewModel.handle != null).toString())
            }
        }

        assertText("true")
    }

    @Test
    fun getViewModelWithNavigationSavedStateStoreOwner() {
        TestViewModel.counter = 0
        val module = module {
            viewModel { TestViewModel() }
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            Koin(appDeclaration = { modules(module) }) {
                NavHost(navController, startDestination = "1", route = "root") {
                    composable("1") {
                        Button(onClick = { navController.navigate("2") }) {
                            Text("go to 2")
                        }
                        val navViewModel = getViewModel<TestViewModel>(
                            viewModelStoreOwner = navController.getBackStackEntry("root")
                        )
                        Text(text = navViewModel.counterText)
                    }
                    composable("2") {
                        Button(onClick = { navController.navigate("1") }) {
                            Text("go to 1")
                        }
                        val navViewModel = getViewModel<TestViewModel>(
                            viewModelStoreOwner = navController.getBackStackEntry("root")
                        )
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

    private fun assertText(text: String, index: Int = 0) {
        composeTestRule.onNode(isRoot()).onChildAt(index).assertTextEquals(text)
    }

    private fun clickOnText(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }
}