package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@PublishedApi
internal val LocalNavController = compositionLocalOf<NavHostController> {
    error("Current Composable is not wrapped in KoinNavigation")
}

@Composable
fun KoinNav(navController: NavHostController, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        content()
    }
}

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

@Composable
fun getNavController(): NavHostController {
    return LocalNavController.current
}