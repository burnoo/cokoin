package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

/**
 * Gets instance from Koin, uses [Scope.get].
 *
 * @param T instance type
 * @param qualifier Koin's [Qualifier]
 * @param parameters Koin's [ParametersDefinition]
 */
@Composable
inline fun <reified T> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val scope = getScope()
    return remember(qualifier, parameters) {
        scope.get(qualifier, parameters)
    }
}