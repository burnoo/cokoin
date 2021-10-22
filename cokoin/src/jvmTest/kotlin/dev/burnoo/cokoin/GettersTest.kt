package dev.burnoo.cokoin

import androidx.compose.material.Text
import dev.burnoo.cokoin.utils.BaseTest
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module

class GettersTest : BaseTest() {

    @Test
    fun useGetKoin() {
        val module = module {
            factory { "text" }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                val text = getKoin().get<String>()
                Text(text)
            }
        }
        assertText("text")
    }

    @Test
    fun useGetScope() {
        val module = module {
            scope(named("scopeA")) {
                scoped { "textA" }
            }
        }

        composeTestRule.setContent {
            Koin(appDeclaration = { modules(module) }) {
                KoinScope(getScope = { createScope("scopeId", named("scopeA"))}) {
                    val text = getScope().get<String>()
                    Text(text = text)
                }
            }
        }
        assertText("textA")
    }
}