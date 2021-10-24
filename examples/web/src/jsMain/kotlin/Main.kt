package dev.burnoo.cokoin.example

import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.get
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.koin.dsl.module

val module = module {
    factory { "Hello, DI!" }
}

fun main() {
    renderComposable(rootElementId = "root") {
        Koin(appDeclaration = { modules(module) }) {
            val text = get<String>()
            Text(text)
        }
    }
}