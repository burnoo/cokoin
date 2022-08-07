package dev.burnoo.cokoin.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import dev.burnoo.cokoin.getScope
import org.koin.androidx.viewmodel.ext.android.getViewModelFactory
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Gets [ViewModel] from Koin. Uses [getViewModelFactory].
 *
 * @param T ViewModel type
 * @param qualifier Koin's [Qualifier]
 * @param parameters Koin's [ParametersDefinition]
 * @param viewModelStoreOwner [ViewModelStoreOwner] used to get [ViewModel]. By default, it uses [LocalViewModelStoreOwner.current]
 */
@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
): T {
    val scope = getScope()
    return remember(qualifier, parameters) {
        val vmClazz = T::class
        val factory = getViewModelFactory(
            owner = viewModelStoreOwner,
            clazz = vmClazz,
            qualifier = qualifier,
            parameters = parameters,
            state = emptyState(),
            scope = scope
        )
        ViewModelProvider(viewModelStoreOwner, factory)[vmClazz.java]
    }
}

@Suppress("UNUSED_PARAMETER", "UNUSED")
@Deprecated(
    "Koin 3.1.3 deprecated state parameter usage. It is not used anymore.",
    ReplaceWith(
        "getViewModel(scope, qualifier, parameters)"
    )
)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
) = getViewModel<T>(qualifier, parameters, viewModelStoreOwner)

@Suppress("UNUSED_PARAMETER", "UNUSED")
@Deprecated(
    message = "Use getViewModel",
    replaceWith = ReplaceWith("getViewModel(qualifier, state, parameters)")
)
@Composable
inline fun <reified T : ViewModel> getStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T = getViewModel(qualifier, parameters)