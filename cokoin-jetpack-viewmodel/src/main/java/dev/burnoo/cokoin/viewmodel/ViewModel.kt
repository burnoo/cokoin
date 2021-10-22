@file:Suppress("DEPRECATION")

package dev.burnoo.cokoin.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.SavedStateHandle
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
 * @param state initial [BundleDefinition] for ViewModels that uses [SavedStateHandle]
 * @param parameters Koin's [ParametersDefinition]
 * @param viewModelStoreOwner [ViewModelStoreOwner] used to get [ViewModel]. By default, it uses [LocalViewModelStoreOwner.current]
 */
@OptIn(KoinInternalApi::class)
@Deprecated(
    message = "`dev.burnoo:cokoin-jetpack-viewmodel` artifact is no longer supported. " +
            "Use `dev.burnoo:cokoin-android-viewmodel` instead.",
    level =  DeprecationLevel.WARNING,
    replaceWith = ReplaceWith("")
)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
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
            state = state,
            owner = { ViewModelOwner.from(storeOwner, savedStateRegistryOwner) },
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