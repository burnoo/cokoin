package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
fun getKoinComponent(): KoinComponent {
    val koin = getKoin()
    val currentScope = getScope()
    return currentScope?.let { scope ->
        object : KoinScopeComponent {
            override val scope = scope
            override fun getKoin() = koin
        }
    } ?: object : KoinComponent {
        override fun getKoin() = koin
    }
}

@Composable
inline fun <reified T> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val scopeComponent = getKoinComponent()
    return remember(qualifier, parameters) {
        scopeComponent.get(qualifier, parameters)
    }
}