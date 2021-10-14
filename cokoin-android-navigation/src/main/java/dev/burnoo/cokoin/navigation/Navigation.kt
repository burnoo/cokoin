package dev.burnoo.cokoin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@PublishedApi
internal val LocalNavController = compositionLocalOf<NavHostController> {
    error("Current Composable is not wrapped in KoinNav or KoinNavHost")
}

/**
 * Composable wrapper to store [NavHostController].
 * Should be used to wrap [NavHost].
 * [getNavViewModel] and [getNavController] works in the Composable content.
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
 * Gets [ViewModel] from Koin using root [NavBackStackEntry] as [ViewModelStoreOwner].
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
    return getViewModel(
        qualifier = qualifier,
        state = state,
        parameters = parameters,
        viewModelStoreOwner = navController.backQueue.first()
    )
}

/**
 * Gets current [NavHostController] instance from the closest parent [KoinNav]
 */
@Composable
fun getNavController(): NavHostController {
    return LocalNavController.current
}