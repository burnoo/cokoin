package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val owner = LocalViewModelStoreOwner.current ?: error("LocalViewModelStoreOwner.current is null")
    val scope = getScope()
    return remember(qualifier, parameters) {
        scope.getViewModel(qualifier, null, { ViewModelOwner.from(owner) }, T::class, parameters)
    }
}

@Composable
inline fun <reified T : ViewModel> getStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val owner = LocalSavedStateRegistryOwner.current
    val scope = getScope()
    return remember(qualifier, parameters) {
        scope.getViewModel(
            qualifier = qualifier,
            state = state,
            owner = { ViewModelOwner.from(owner as ViewModelStoreOwner, owner) },
            clazz = T::class,
            parameters = parameters
        )
    }
}