package dev.burnoo.cokoin.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
 * @param state initial [BundleDefinition] for ViewModels that uses [SavedStateHandle]
 * @param parameters Koin's [ParametersDefinition]
 */
@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("LocalViewModelStoreOwner.current is null")
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val scope = getScope()
    return remember(qualifier, parameters) {
        scope.getViewModel(
            qualifier = qualifier,
            state = state,
            owner = { ViewModelOwner.from(viewModelStoreOwner, savedStateRegistryOwner) },
            clazz = T::class,
            parameters = parameters
        )
    }
}

@Deprecated(
    message = "Use getViewModel",
    replaceWith = ReplaceWith("getViewModel(qualifier, state, parameters)")
)
@Composable
inline fun <reified T : ViewModel> getStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T = getViewModel(qualifier, state, parameters)