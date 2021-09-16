package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope

@PublishedApi
internal val LocalScope = compositionLocalOf<Scope> {
    error("Koin is not initialized, make sure that your composable is inside Koin Composable")
}

@Composable
fun KoinScope(
    getScope: Koin.() -> Scope,
    content: @Composable () -> Unit
) {
    val koin = getKoin()
    val scope = remember {
        koin.getScope()
    }
    CompositionLocalProvider(LocalScope provides scope) {
        content()
    }
}

@Composable
fun getScope() = LocalScope.current