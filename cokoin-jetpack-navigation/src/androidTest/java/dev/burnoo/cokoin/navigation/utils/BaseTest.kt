package dev.burnoo.cokoin.navigation.utils

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    protected fun assertText(text: String, index: Int = 0) {
        composeTestRule.onNode(isRoot()).onChildAt(index).assertTextEquals(text)
    }
}