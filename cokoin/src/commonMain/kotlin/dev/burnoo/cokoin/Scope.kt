package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope

@OptIn(KoinInternalApi::class)
@PublishedApi
internal val LocalScope = compositionLocalOf {
    getGlobalKoin().scopeRegistry.rootScope
}

/**
 * Composable wrapper for [Scope].
 * Nesting this function adds parent scope with [Scope.linkTo]
 *
 * @param getScope function to get or create [Scope] using current [Koin] as receiver
 * @param content Composable content, [Scope] is set within it
 */
@Composable
fun KoinScope(
    getScope: Koin.() -> Scope,
    content: @Composable () -> Unit
) {
    val currentScope = LocalScope.current
    val koin = getKoin()
    val scope = remember {
        koin.getScope().also {
            it.linkTo(currentScope)
        }
    }
    CompositionLocalProvider(LocalScope provides scope) {
        content()
    }
}

/**
 * Gets current [Scope] from the closest parent [dev.burnoo.cokoin.KoinScope]
 */
@Composable
fun getScope() = LocalScope.current