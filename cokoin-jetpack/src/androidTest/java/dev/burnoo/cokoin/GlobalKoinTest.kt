package dev.burnoo.cokoin

import androidx.compose.material.Text
import dev.burnoo.cokoin.utils.BaseTest
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTestRule

class GlobalKoinTest : BaseTest() {

    private val testModule = module {
        factory { "testString" }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    @Test
    fun displayTextFromGlobalKoin() {
        composeTestRule.setContent {
            Text(get<String>())
        }
        assertText("testString")
    }
}