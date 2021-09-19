package dev.burnoo.cokoin

import androidx.compose.material.Text
import dev.burnoo.cokoin.utils.BaseTest
import org.junit.Test
import org.koin.dsl.module

private object ScopeA
private object ScopeB

class CokoinTest : BaseTest() {

    @Test
    fun getTextFromKoinModule() {
        val module = module {
            factory { "text" }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val text = get<String>()
                Text(text)
            }
        }
        assertText("text")
    }

    @Test
    fun getTextFromScopes() {
        val module = module {
            scope<ScopeA> {
                scoped { "textA" }
            }
            scope<ScopeB> {
                scoped { "textB" }
            }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                KoinScope(getScope = { createScope<ScopeA>() }) {
                    Text(get<String>())
                }
                KoinScope(getScope = { createScope<ScopeB>() }) {
                    Text(get<String>())
                }
            }
        }
        assertText("textA", index = 0)
        assertText("textB", index = 1)
    }
}