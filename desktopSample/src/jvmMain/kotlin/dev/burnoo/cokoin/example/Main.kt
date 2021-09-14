package dev.burnoo.cokoin.example

import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.get
import org.koin.dsl.module

val module = module {
    factory { "Hello, DI!" }
}

fun main() = application {
    Koin(appDeclaration = { modules(module) }) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Cokoin example",
            state = WindowState(
                position = WindowPosition.Aligned(Alignment.Center),
            ),
        ) {
            val text = get<String>()
            Text(text)
        }
    }
}