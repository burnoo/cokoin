package dev.burnoo.cokoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@PublishedApi
internal val LocalNavController = compositionLocalOf<NavController> {
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
inline fun <reified T : ViewModel> getNavViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val navController = getNavController()
    val scope = getScope()
    return remember(qualifier, parameters) {
        val navBackStackEntry = navController.backQueue.first()
        scope.getViewModel(qualifier, null, { ViewModelOwner.from(navBackStackEntry) }, T::class, parameters)
    }
}

@Composable
fun getNavController(): NavController {
    return LocalNavController.current
}