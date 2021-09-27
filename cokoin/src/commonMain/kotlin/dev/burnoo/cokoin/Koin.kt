package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinInternalApi
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication

@PublishedApi
internal val LocalKoin = compositionLocalOf { getGlobalKoin() }

/**
 * Composable wrapper for [Koin].
 *
 * @param koinApplication definition of [KoinApplication] to be used within content
 * @param content Composable content, injection works within it
 */
@OptIn(KoinInternalApi::class)
@Composable
fun Koin(
    koinApplication: KoinApplication,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalKoin provides koinApplication.koin,
        LocalScope provides koinApplication.koin.scopeRegistry.rootScope
    ) {
        content()
    }
}

/**
 * Composable wrapper for [Koin].
 *
 * @param appDeclaration [KoinAppDeclaration] used to create Koin Application
 * @param content Composable content, injection works within it
 */
@Composable
fun Koin(
    appDeclaration: KoinAppDeclaration? = null,
    content: @Composable () -> Unit
) {
    Koin(koinApplication(appDeclaration)) {
        content()
    }
}

/**
 * Gets current [Koin] instance from the closest parent [dev.burnoo.cokoin.Koin].
 */
@Composable
fun getKoin(): Koin = LocalKoin.current