package dev.burnoo.cokoin

import androidx.compose.material.Text
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.burnoo.cokoin.utils.BaseTest
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private class TestViewModel(
    val text: String = "ViewModelText",
    val handle: SavedStateHandle? = null
) : ViewModel()

class ViewModelTest : BaseTest() {

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
            viewModel { params -> TestViewModel(params.get(), params.get()) }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val viewModel =
                    getViewModel<TestViewModel>(parameters = {
                        parametersOf(null, "ParameterText")
                    })
                Text(viewModel.text)
            }
        }

        assertText("ParameterText")
    }

    @Test
    fun getStateViewModelFromKoinModule() {
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
}