package dev.burnoo.cokoin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.burnoo.cokoin.getScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

@PublishedApi
internal val LocalNavController = compositionLocalOf<NavHostController> {
    error("Current Composable is not wrapped in KoinNav or KoinNavHost")
}

/**
 * Composable wrapper to store [NavHostController].
 * Should be used to wrap [NavHost].
 * [getNavViewModel] works in Composable content.
 *
 * @param navController [NavHostController]
 * @param content container for [NavHost]
 */
@Composable
fun KoinNav(navController: NavHostController, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        content()
    }
}

/**
 * Short for wrapping [NavHost] with [KoinNav].
 * Parameters: the same as [NavHost].
 */
@Composable
fun KoinNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    KoinNav(navController) {
        NavHost(navController, startDestination, modifier, route, builder)
    }
}

/**
 * Short for wrapping [NavHost] with [KoinNav].
 * Parameters: the same as [NavHost].
 */
@Composable
fun KoinNavHost(
    navController: NavHostController,
    graph: NavGraph,
    modifier: Modifier = Modifier
) {
    KoinNav(navController) {
        NavHost(navController, graph, modifier)
    }
}

/**
 * Gets [ViewModel] from Koin using [LocalSavedStateRegistryOwner].
 * Uses [Scope.getViewModel].
 * Needs to be called inside [KoinNav] or [KoinNavHost].
 *
 * @param T ViewModel type
 * @param qualifier Koin's [Qualifier]
 * @param state Initial [BundleDefinition] for ViewModels that uses [SavedStateHandle]
 * @param parameters Koin's [ParametersDefinition]
 */
@Composable
inline fun <reified T : ViewModel> getNavViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val navController = getNavController()
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val scope = getScope()
    return remember(qualifier, parameters) {
        val navBackStackRootEntry = navController.backQueue.first()
        scope.getViewModel(
            qualifier = qualifier,
            state = state,
            owner = { ViewModelOwner.from(navBackStackRootEntry, savedStateRegistryOwner) },
            clazz = T::class,
            parameters = parameters
        )
    }
}

/**
 * Gets current [NavHostController] instance from the closest parent [KoinNav]
 */
@Composable
fun getNavController(): NavHostController {
    return LocalNavController.current
}