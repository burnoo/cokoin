package dev.burnoo.cokoin.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import dev.burnoo.cokoin.getScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

/**
 * Gets [ViewModel] from Koin. Uses [Scope.getViewModel].
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
    viewModelStoreOwner: ViewModelStoreOwner? = null
): T {
    val storeOwner = viewModelStoreOwner
        ?: LocalViewModelStoreOwner.current
        ?: error("LocalViewModelStoreOwner.current is null")
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val scope = getScope()
    return remember(qualifier, parameters) {
        scope.getViewModel(
            qualifier = qualifier,
            owner = { ViewModelOwner.from(storeOwner, savedStateRegistryOwner) },
            parameters = parameters
        )
    }
}

@Deprecated(
    "Koin 3.1.3 deprecated state parameter usage. It is not used anymore.",
    ReplaceWith(
        "getViewModel(scope, qualifier, parameters)"
    )
)
@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
    viewModelStoreOwner: ViewModelStoreOwner? = null
) = getViewModel<T>(qualifier, parameters, viewModelStoreOwner)

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